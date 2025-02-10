package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class DmCode extends ElementXML {

    private static final String HTML_H2 = "h2";
    private static final String HTML_SPAN = "span";
    private static final String S1000D_DMC = "DMC: ";
    private static final String DASH = "-";
    
    DmCode() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("dmCode");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        body.appendElement(HTML_H2).text(S1000D_DMC);
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
        Element span = body.appendElement(HTML_SPAN).text(dmc.toString());
        this.appendChildrenToElement(span);
        return doc.toString();
    }

}
