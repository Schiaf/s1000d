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
public class SupplyDescrGroup extends ElementXML {

    SupplyDescrGroup() {
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
        Element supplyTable = body.appendElement(HTML_TABLE).addClass(this.getClass().getSimpleName());
        supplyTable.attributes().put("id", this.getAttribute("id"));
        List<String> usedAttributes = Arrays.asList("id");
        Element supplyTableHeader = supplyTable.appendElement(HTML_THEAD).appendElement(HTML_TR);
        supplyTableHeader.appendElement(HTML_TH).text("Name");
        supplyTableHeader.appendElement(HTML_TH).text("MFC - PNR");
        supplyTableHeader.appendElement(HTML_TH).text("Quantity");
        supplyTableHeader.appendElement(HTML_TH).text("Hazard");
        Element supplyTableBody = supplyTable.appendElement(HTML_TBODY);
        this.appendTagToElement(supplyTableBody, "tr", usedAttributes);
        
        return doc.toString();
    }

}
