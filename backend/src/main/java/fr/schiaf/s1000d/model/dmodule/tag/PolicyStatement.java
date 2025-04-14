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
public class PolicyStatement extends ElementXML {

    private static final String S1000D_TITLE = "Policy Statement: ";

    PolicyStatement() {
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
        Element div = body.appendElement(HTML_DIV).addClass("indented");
        div.appendElement(HTML_SPAN).text(S1000D_TITLE).addClass("bold");
        Element span = div.appendElement(HTML_SPAN);
        List<String> usedAttributes = Arrays.asList("");
        this.appendChildrenToElement(span, usedAttributes);
        return doc.toString();
    }

}
