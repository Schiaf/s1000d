package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Component
public class Dmodule extends ElementXML {

    Dmodule() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("dmodule");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        //get the head element
        Element head = doc.head();
        //add the title
        head.appendElement("title").text(this.getName());
        //get the body element
        Element body = doc.body();
        //add the children
        for (ElementXML child : this.getChildren()) {
            String childHtml = child.toHtml();
            Document childDoc = Jsoup.parse(childHtml);
            for (Element element : childDoc.body().children()) {
                body.appendChild(element);
            }
        }
        return doc.toString();
    }

    @Override
    public String toS1000DXml() {
        Document doc = new Document("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        XmlDeclaration xmlDeclaration = new XmlDeclaration("xml", false);
        xmlDeclaration.attr("version", "1.0");
        xmlDeclaration.attr("encoding", "UTF-8");
        doc.prependChild(xmlDeclaration);
        doc.prependChild(new DocumentType(this.getName(), "", ""));

        Element root = doc.appendElement(this.getName());
        List<ElementXML> attributes = getAttributes();
        if (attributes != null) {
            for (ElementXML attribute : attributes) {
                if (attribute != null && attribute.getChildren() != null && !attribute.getChildren().isEmpty()) {
                    root.attr(attribute.getName(), attribute.getChildren().get(0).toS1000DXml());
                }
            }
        }

        for (ElementXML child : this.getChildren()) {
            root.append(child.toS1000DXml());
        }

        return doc.toString();
    }

}
