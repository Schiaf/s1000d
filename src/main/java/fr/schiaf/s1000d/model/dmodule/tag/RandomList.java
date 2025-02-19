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
public class RandomList extends ElementXML {

    RandomList() {
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
        Element ul = body.appendElement(HTML_UL);
        String prefix = this.getAttribute("listItemPrefix");
        if (prefix != null && !prefix.isEmpty()) {
            ul.addClass(prefix);
        }
        List<String> usedAttributes = Arrays.asList("listItemPrefix");

        this.appendChildrenToElement(ul, usedAttributes);
        return doc.toString();
    }

}
