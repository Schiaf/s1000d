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
public class Language extends ElementXML {

    private static final String S1000D_LANG = "Language: ";
    
    Language() {
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
        Element div = body.appendElement(HTML_DIV);
        List<String> usedAttributes = Arrays.asList("languageIsoCode", "countryIsoCode");
        Element span = div.appendElement(HTML_SPAN).text(S1000D_LANG);
        span.addClass("bold");
        StringBuilder lang = new StringBuilder();
        lang.append(this.getAttribute("languageIsoCode"));
        lang.append(DASH);
        lang.append(this.getAttribute("countryIsoCode"));
        //add other attributes
        div.appendElement(HTML_SPAN).text(lang.toString());
        this.appendChildrenToElement(div, usedAttributes);
        return doc.toString();
    }

}
