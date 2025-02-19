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
public class DmCode extends ElementXML {

    private static final String S1000D_DMC = "DMC: ";
    
    DmCode() {
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
        List<String> usedAttributes = Arrays.asList("modelIdentCode", "systemDiffCode", "systemCode", "subSystemCode", "subSubSystemCode", "assyCode", "disassyCode", "disassyCodeVariant", "infoCode", "infoCodeVariant", "itemLocationCode"); 
        Element span = div.appendElement(HTML_SPAN).appendElement(HTML_SPAN).text(S1000D_DMC);
        span.addClass("bold");
        StringBuilder dmc = new StringBuilder();
        dmc.append(this.getAttribute("modelIdentCode"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("systemDiffCode"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("systemCode"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("subSystemCode"));
        dmc.append(this.getAttribute("subSubSystemCode"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("assyCode"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("disassyCode"));
        dmc.append(this.getAttribute("disassyCodeVariant"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("infoCode"));
        dmc.append(this.getAttribute("infoCodeVariant"));
        dmc.append(DASH);
        dmc.append(this.getAttribute("itemLocationCode"));
        div.appendElement(HTML_SPAN).attr("name", "dmc").text(dmc.toString());
        this.appendChildrenToElement(div, usedAttributes);

        return doc.toString();
    }

}
