package fr.schiaf.s1000d.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Service
public class ElementService {

    @Autowired
    private ElementFactory elementFactory;

    public void processElements() {
        ElementXML dmodule = elementFactory.createElement("dmodule", ElementType.TAG, null);
        ElementXML identAndStatusSection = elementFactory.createElement("identAndStatusSection", ElementType.TAG, dmodule);
        ElementXML dmAddress = elementFactory.createElement("dmAddress", ElementType.TAG, identAndStatusSection);
        ElementXML dmIdent = elementFactory.createElement("dmIdent", ElementType.TAG, dmAddress);
        ElementXML dmCode = elementFactory.createElement("dmCode", ElementType.TAG, dmIdent);
        ElementXML modelIdentCode = elementFactory.createElement("modelIdentCode", ElementType.ATTRIBUTE, dmCode);
        ElementXML modelIdentCodeText = elementFactory.createElement("text", ElementType.TEXT, modelIdentCode);
        modelIdentCodeText.setName("BRAKE");
        modelIdentCode.getChildren().add(modelIdentCodeText);
        dmCode.getAttributes().add(modelIdentCode);
        dmIdent.getChildren().add(dmCode);
        dmAddress.getChildren().add(dmIdent);
        identAndStatusSection.getChildren().add(dmAddress);
        dmodule.getChildren().add(identAndStatusSection);
        System.out.println(dmodule.toS1000DXml());
        System.out.println(dmodule.toHtml());
    }

    //readFile
    public void processElementsFromFile(String filePath) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", "",  Parser.xmlParser());
            ElementXML root = null;
            root = addChildrens(root, doc.childNodes());
            //write root.toHtml() to file "output.html"
            File output = new File("output.html");
            FileUtils.writeStringToFile(output, root.toHtml(), "UTF-8");
            //copy css file to the same directory
            File css = new File("dmodule.css");
            FileUtils.copyFile(new File("src/main/resources/fr/schiaf/s1000d/css/dmodule.css"), css);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ElementXML addChildrens(ElementXML element, List<Node> children) {
        for (Node child : children) {
            //switch case for different types of nodes
            if (child instanceof Element) {
                ElementXML newElement = elementFactory.createElement(((Element) child).tagName(),ElementType.TAG, element);
                for (org.jsoup.nodes.Attribute attribute : ((Element) child).attributes()) {
                    ElementXML newAttribute = elementFactory.createElement(attribute.getKey(), ElementType.ATTRIBUTE, newElement);
                    ElementXML newAttributeValue = elementFactory.createElement("text", ElementType.TEXT, newAttribute);
                    newAttributeValue.setName(attribute.getValue());
                    newAttribute.getChildren().add(newAttributeValue);
                    newElement.getAttributes().add(newAttribute);
                }
                addChildrens(newElement, ((Element) child).childNodes());

                if (element == null) {
                    element = newElement;
                }else{
                    element.getChildren().add(newElement);
                }
            }else if (child instanceof org.jsoup.nodes.TextNode) {
                ElementXML newElement = elementFactory.createElement("text", ElementType.TEXT, element);
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