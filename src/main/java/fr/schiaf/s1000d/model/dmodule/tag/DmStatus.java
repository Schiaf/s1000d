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
public class DmStatus extends ElementXML {

    private static final String S1000D_ISSTYPE = "Issue Type: ";
    
    DmStatus() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        Element div = body.appendElement(HTML_DIV);
        List<String> usedAttributes = Arrays.asList("issueType");
        Element span = div.appendElement(HTML_SPAN).text(S1000D_ISSTYPE);
        span.addClass("bold");
        StringBuilder lang = new StringBuilder();
        lang.append(this.getAttribute("issueType"));
        //add other attributes
        div.appendElement(HTML_SPAN).text(lang.toString());
        this.appendChildrenToElement(div, usedAttributes);

    return doc.toString();
    }
}
