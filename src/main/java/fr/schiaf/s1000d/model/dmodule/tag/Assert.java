package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class Assert extends ElementXML {
    
    Assert() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        Element span = body.appendElement(HTML_SPAN);
        List<String> usedAttributes = Arrays.asList("applicPropertyIdent", "applicPropertyValues", "applicPropertyType");
        StringBuilder content = new StringBuilder();
        content.append("[");
        content.append(this.getAttribute("applicPropertyIdent"));
        content.append(": ");
        content.append(this.getAttribute("applicPropertyValues"));
        content.append("]");
        span.text(content.toString());
        if (this.getNextSibling() != null) {
            ElementXML parent = this.getParent();
            if (parent != null) {
                body.appendElement(HTML_SPAN).append(this.getParent().getAttribute("andOr")).addClass("andOr");
            }
        }
        //add other attributes
        span.text(content.toString());
        this.appendChildrenToElement(span, usedAttributes);
        return doc.toString();
    }

}
