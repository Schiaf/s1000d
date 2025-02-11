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
public class ListItem extends ElementXML {

    ListItem() {
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
        Element li = body.appendElement(HTML_LI);
        this.appendChildrenToElement(li);
        li.getElementsByTag(HTML_P).forEach(p -> {
            p.addClass("inline");
        });
        List<String> usedAttributes = Arrays.asList("");
        Element span2 = this.addMissingAttribute(usedAttributes);
        if (span2.childrenSize() != 0) {
            body.appendChild(span2);
        }
        return doc.toString();
    }

}
