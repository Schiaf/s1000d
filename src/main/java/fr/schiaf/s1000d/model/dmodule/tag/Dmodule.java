package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Component
@Scope("prototype")
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
        this.appendChildrenToElement(body);
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
