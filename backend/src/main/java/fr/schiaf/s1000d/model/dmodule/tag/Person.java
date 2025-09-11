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
public class Person extends ElementXML {

    Person() {
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
        Element personTable = body.appendElement(HTML_TABLE).addClass(this.getClass().getSimpleName());
        personTable.attributes().put("id", this.getAttribute("id"));
        List<String> usedAttributes = Arrays.asList("id", "man");
        Element personTableHeader = personTable.appendElement(HTML_THEAD).appendElement(HTML_TR);
        personTableHeader.appendElement(HTML_TH).text("Man");
        personTableHeader.appendElement(HTML_TH).text("Category");
        personTableHeader.appendElement(HTML_TH).text("Trade");
        personTableHeader.appendElement(HTML_TH).text("Time");
        Element personTableBody = personTable.appendElement(HTML_TBODY);
        Element personTr = personTableBody.appendElement(HTML_TR);
        personTr.appendElement(HTML_TD).text(this.getAttribute("man"));
        this.appendTagToElement(personTr, "td", usedAttributes);
        
        return doc.toString();
    }

}
