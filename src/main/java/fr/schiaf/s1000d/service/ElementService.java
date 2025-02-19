package fr.schiaf.s1000d.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.DataModule;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Service
public class ElementService {

    @Autowired
    private ElementFactory elementFactory;

    //readFile
    public void processElementsFromFile(String filePath) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", "",  Parser.xmlParser());
            DataModule dataModule = new DataModule();
            dataModule.setXmlDeclaration(getXMLDeclaration(doc.childNodes()));
            dataModule.setDoctype(getDoctype(doc.childNodes()));
            dataModule.setEntities(getEntities(doc.childNodes()));
            ElementXML root = null;
            root = addChildrens(root, doc.childNodes(), dataModule);
            dataModule.setRoot(root);

            //write root.toHtml() to file "output.html"
            File output = new File("output.html");
            FileUtils.writeStringToFile(output, dataModule.getRoot().toHtml(), "UTF-8");
            //copy css file to the same directory
            File css = new File("dmodule.css");
            FileUtils.copyFile(new File("src/main/resources/fr/schiaf/s1000d/css/dmodule.css"), css);
        } catch (IOException e) {
            e.printStackTrace();
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
            if (child instanceof org.jsoup.nodes.Comment) {
                if (((org.jsoup.nodes.Comment) child).getData().contains("ENTITY")) {
                    // split <!ENTITY ICN-C0419-S1000D0381-001-01 SYSTEM "ICN-C0419-S1000D0381-001-01.CGM" NDATA cgm> to get entity name and value
                    String[] entity = ((org.jsoup.nodes.Comment) child).getData().split(" ");
                    entities.put(entity[1], entity[3]);
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