package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class DmRefIdent extends ElementXML {

    DmRefIdent() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        String childHtml = this.getChildren().get(0).toHtml();
        Document childDoc = Jsoup.parse(childHtml);
        for (Node element : childDoc.body().childNodes()) {
            body.appendChild(element);
        }
        List<String> usedAttributes = Arrays.asList("");
        List<String> usedTags = Arrays.asList(this.getChildren().get(0).getName());
        this.appendChildrenToElement(body, usedAttributes, usedTags);

        return doc.toString();
    }

}
