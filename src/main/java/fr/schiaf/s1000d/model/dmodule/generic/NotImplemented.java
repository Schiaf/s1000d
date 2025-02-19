package fr.schiaf.s1000d.model.dmodule.generic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class NotImplemented extends ElementXML {

    NotImplemented() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("");
        this.setType(ElementType.TEXT);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        List<String> usedAttributes = Arrays.asList("");
        Element div = body.appendElement(HTML_DIV);
        StringBuilder sb = new StringBuilder();
        sb.append("Not implemented: ");
        if (this.getType() == ElementType.ATTRIBUTE) {
            sb.append("@");
        }
        sb.append(this.getName());
        if (this.getType() == ElementType.ATTRIBUTE) {
            sb.append(": ");
        }
        Element div2 = body.appendElement(HTML_DIV).text(sb.toString()).addClass("nottreated");
        this.appendChildrenToElement(div2, usedAttributes);
        //add div2 to div
        div.appendChild(div2);
        return doc.toString();
    }

}
