package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class Dmodule extends ElementXML {

    Dmodule() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
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
        //add the meta charset
        head.appendElement("meta").attr("charset", "UTF-8");
        //add the meta viewport
        head.appendElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1");
        //add the css link
        head.appendElement("link").attr("rel", "stylesheet").attr("href", "https://www.w3schools.com/w3css/4/w3.css");
        //add dmodule.css file
        head.appendElement("link").attr("rel", "stylesheet").attr("href", "dmodule.css");
        //get the body element
        Element body = doc.body();
        Element div = body.appendElement(HTML_DIV);
        Element h1Element = div.appendElement(HTML_H1);
        body.appendChild(div);
        div.appendChild(h1Element);
        StringBuilder dmc = new StringBuilder();
        dmc.append("DMC-");
        DmCode dmCode = (DmCode) this.getElementXMLbyName("dmCode");
        dmc.append(dmCode.getDmc());
        h1Element.text(dmc.toString());
        List<String> usedAttributes = Arrays.asList("dc", "rdf", "xsi", "xlink", "noNamespaceSchemaLocation");
        //add the children
        this.appendChildrenToElement(body, usedAttributes);
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
        doc.append(super.toS1000DXml());
        return doc.toString();
    }

}
