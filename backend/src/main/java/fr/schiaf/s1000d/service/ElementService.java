package fr.schiaf.s1000d.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.DataModule;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Service
public class ElementService {

    @Autowired
    private ElementFactory elementFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    //readFile
    public void processElementsFromFile(String filePath) {
        try {
            File input = new File(filePath);
            //Add fake entity in input content <!ENTITY jsoup SYSTEM "jsoup" NDATA jsoup> after <!DOCTYPE dmodule [ for jsoup bug
            String content = FileUtils.readFileToString(input, "UTF-8");
            content = content.replace("<!DOCTYPE dmodule [", "<!DOCTYPE dmodule [\n<!ENTITY jsoup SYSTEM \"jsoup\" NDATA jsoup>");
            FileUtils.writeStringToFile(input, content, "UTF-8");
            Document doc = Jsoup.parse(input, "UTF-8", "",  Parser.xmlParser());
            DataModule dataModule = new DataModule();
            dataModule.setXmlDeclaration(getXMLDeclaration(doc.childNodes()));
            dataModule.setDoctype(getDoctype(doc.childNodes()));
            dataModule.setEntities(getEntities(doc.childNodes()));
            ElementXML root = null;
            root = addChildrens(root, doc.childNodes(), dataModule);
            dataModule.setRoot(root);

            // Write output.html
            File outputHtml = new File("output.html");
            FileUtils.writeStringToFile(outputHtml, dataModule.getRoot().toHtml(), "UTF-8");

            //write img present in entities and list in imgList
            List<File> imgList = new ArrayList<>();
            for (Map.Entry<String, String> entry : dataModule.getEntities().entrySet()) {
                String entityPath = entry.getValue().replace("\"", "");
                // if extension is .cgm ignore case, replace by svg
                if (entityPath.toUpperCase().endsWith(".CGM")) {
                    entityPath = entityPath.replace(".CGM", ".SVG");
                }
                Resource imgResource = resourceLoader.getResource("classpath:fr/schiaf/s1000d/img/" + entityPath);
                File imgFile = new File(entityPath);
                FileUtils.copyInputStreamToFile(imgResource.getInputStream(), imgFile);
                imgList.add(imgFile);  
            }

            // Exemple pour charger dmodule.css depuis le classpath
            Resource cssResource = resourceLoader.getResource("classpath:fr/schiaf/s1000d/css/dmodule.css");
            File css = new File("dmodule.css");
            FileUtils.copyInputStreamToFile(cssResource.getInputStream(), css);

            // Exemple pour charger NotSupported.png depuis le classpath
            Resource imgResource = resourceLoader.getResource("classpath:fr/schiaf/s1000d/img/NotSupported.png");
            File imgNotSuppFile = new File("NotSupported.png");
            FileUtils.copyInputStreamToFile(imgResource.getInputStream(), imgNotSuppFile);

            // Create output.zip
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("output.zip"))) {
                addFileToZip(outputHtml, "", zos);
                addFileToZip(css, "", zos);
                addFileToZip(imgNotSuppFile, "", zos);
                // Add all images from imgList
                for (File imgFile : imgList) {
                    addFileToZip(imgFile, "", zos);
                }
            }

            // Optionally, delete the temp files after zipping
            outputHtml.delete();
            css.delete();
            imgNotSuppFile.delete();
            for (File imgFile : imgList) {
                imgFile.delete();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Helper method to add a file to the ZIP
    private void addFileToZip(File file, String parentDir, ZipOutputStream zos) throws IOException {
        if (file == null || !file.exists()) return;
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(parentDir + file.getName());
            zos.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        }
    }

    private String getXMLDeclaration(List<Node> childNodes) {
        for (Node child : childNodes) {
            if (child instanceof org.jsoup.nodes.XmlDeclaration) {
                return ((org.jsoup.nodes.XmlDeclaration) child).getWholeDeclaration();
            }
        }
        return null;
    }
           
    private String getDoctype(List<Node> childNodes) {
        for (Node child : childNodes) {
            if (child instanceof org.jsoup.nodes.DocumentType) {
                String doctype = ((org.jsoup.nodes.DocumentType) child).toString();
                return doctype;
            }
        }
        return null;
    }

    private Map<String, String> getEntities(List<Node> childNodes) {
        Map<String, String> entities = new java.util.HashMap<>();
        for (Node child : childNodes) {
            if (child instanceof org.jsoup.nodes.XmlDeclaration) {
                String decl = ((org.jsoup.nodes.XmlDeclaration) child).getWholeDeclaration();
                String name = ((org.jsoup.nodes.XmlDeclaration) child).name();
                if (name.equalsIgnoreCase("ENTITY")) {
                    // split <!ENTITY ICN-C0419-S1000D0381-001-01 SYSTEM "ICN-C0419-S1000D0381-001-01.CGM" NDATA cgm> to get entity name and value
                    String[] entity = decl.split(" ");
                    entities.put(entity[0], entity[2]);
                }
            }
        }
        return entities;
    }

    public ElementXML addChildrens(ElementXML element, List<Node> children, DataModule dataModule) {
        for (Node child : children) {
            //switch case for different types of nodes
            if (child instanceof Element) {
                ElementXML newElement = elementFactory.createElement(((Element) child).tagName(),ElementType.TAG, element, dataModule);
                for (org.jsoup.nodes.Attribute attribute : ((Element) child).attributes()) {
                    ElementXML newAttribute = elementFactory.createElement(attribute.getKey(), ElementType.ATTRIBUTE, newElement, dataModule);
                    ElementXML newAttributeValue = elementFactory.createElement("text", ElementType.TEXT, newAttribute, dataModule);
                    newAttributeValue.setName(attribute.getValue());
                    newAttribute.getChildren().add(newAttributeValue);
                    newElement.getAttributes().add(newAttribute);
                }
                addChildrens(newElement, ((Element) child).childNodes(), dataModule);

                if (element == null) {
                    element = newElement;
                }else{
                    element.getChildren().add(newElement);
                }
            }else if (child instanceof org.jsoup.nodes.TextNode) {
                ElementXML newElement = elementFactory.createElement("text", ElementType.TEXT, element, dataModule);
                //if text node is not empty
                if (!((org.jsoup.nodes.TextNode) child).text().trim().isEmpty()){
                    //get parent type node
                    if (!child.parent().nodeName().equalsIgnoreCase("#document")){
                        newElement.setName(((org.jsoup.nodes.TextNode) child).text());
                        if (element == null) {
                            element = newElement;
                        }else{
                            element.getChildren().add(newElement);
                        }
                    }
                }
            }
        }
        return element;
    }
}