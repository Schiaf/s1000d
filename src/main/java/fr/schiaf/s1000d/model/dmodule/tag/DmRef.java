package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class DmRef extends ElementXML {

    DmRef() {
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
        Element anchor = body.appendElement(HTML_A);
        // get the dmCode from descendant 
        String dmCodeHtml = this.getChildren().get(0).toHtml();
        Document childDoc = Jsoup.parse(dmCodeHtml);
        String dmchref = childDoc.body().getElementsByAttributeValue("name", "dmc").text();
        anchor.attr(HTML_HREF, childDoc.text()).text(dmchref);
        List<String> usedAttributes = Arrays.asList("");
        List<String> usedTags = Arrays.asList(this.getChildren().get(0).getName());
        this.appendChildrenToElement(anchor, usedAttributes, usedTags);

        return doc.toString();
    }

}
