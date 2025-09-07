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
public class EstimatedTime extends ElementXML {

    
    EstimatedTime() {
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
        Element td = body.appendElement(HTML_TABLE)
        .appendElement(HTML_TBODY)
        .appendElement(HTML_TR)
        .appendElement(HTML_TD);
        List<String> usedAttributes = Arrays.asList("unitOfMeasure");
        Element span = td.appendElement(HTML_SPAN).addClass(this.getClass().getSimpleName());
        td.appendText(this.getAttribute("unitOfMeasure"));
        this.appendChildrenToElement(span, usedAttributes);
        return doc.toString();
    }

}
