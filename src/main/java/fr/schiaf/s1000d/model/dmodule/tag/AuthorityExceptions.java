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
public class AuthorityExceptions extends ElementXML {

    private static final String S1000D_TITLE = "Authority Exceptions: ";
    
    AuthorityExceptions() {
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
        List<String> usedAttributes = Arrays.asList("");
        Element span = div.appendElement(HTML_SPAN).text(S1000D_TITLE);
        span.addClass("bold");
        this.appendChildrenToElement(div, usedAttributes);
        return doc.toString();
    }

}
