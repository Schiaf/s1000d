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
public class IdentAndStatusSection extends ElementXML {
    private static final String HTML_H1= "h1";
    private static final String HTML_DIV = "div";

    private static final String S1000D_STATUS= "STATUS";



    IdentAndStatusSection() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("identAndStatusSection");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        body.appendElement(HTML_H1).text(S1000D_STATUS);
        Element div = body.appendElement(HTML_DIV);

        for (ElementXML child : this.getChildren()) {
            div.append(child.toHtml());
        }

        return doc.toString();
    }

    @Override
    public String toS1000DXml() {
        Document doc = new Document("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
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
