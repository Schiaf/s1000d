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
public class Security extends ElementXML {

    private static final String S1000D_SECURITY = "Security Class: ";
    private static final String S1000D_COMMERCIAL = "Commercial Class: ";
    private static final String S1000D_CAVEAT = "Caveat: ";
    
    Security() {
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
        List<String> usedAttributes = Arrays.asList("securityClassification", "commercialClassification", "caveat");

        Element divtmp = div.appendElement(HTML_DIV);
        divtmp.appendElement(HTML_SPAN).addClass("bold").text(S1000D_SECURITY);
        StringBuilder securityclass = new StringBuilder();
        securityclass.append(this.getAttribute("securityClassification"));
        divtmp.appendElement(HTML_SPAN).text(securityclass.toString());

        divtmp = div.appendElement(HTML_DIV);
        divtmp.appendElement(HTML_SPAN).addClass("bold").text(S1000D_COMMERCIAL);
        StringBuilder commercialclass = new StringBuilder();
        commercialclass.append(this.getAttribute("commercialClassification"));
        divtmp.appendElement(HTML_SPAN).text(commercialclass.toString());

        divtmp = div.appendElement(HTML_DIV);
        divtmp.appendElement(HTML_SPAN).addClass("bold").text(S1000D_CAVEAT);
        StringBuilder caveat = new StringBuilder();
        caveat.append(this.getAttribute("caveat"));
        divtmp.appendElement(HTML_SPAN).text(caveat.toString());

        this.appendChildrenToElement(div, usedAttributes);
        return doc.toString();
    }

}
