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
public class IssueDate extends ElementXML {

    private static final String S1000D_ISSUE = "Issue Date: ";
    
    IssueDate() {
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
        Element span = div.appendElement(HTML_SPAN).appendElement(HTML_SPAN).text(S1000D_ISSUE);
        span.addClass("bold");
        StringBuilder issue = new StringBuilder();
        issue.append(this.getAttribute("year"));
        issue.append(DASH);
        issue.append(this.getAttribute("month"));
        issue.append(DASH);
        issue.append(this.getAttribute("day"));
        div.appendElement(HTML_SPAN).text(issue.toString());
        this.appendChildrenToElement(div);
        List<String> usedAttributes = Arrays.asList("year", "month", "day");
        Element span2 = this.addMissingAttribute(usedAttributes);
        if (span2.childrenSize() != 0) {
            div.appendChild(span2);
        }
        return doc.toString();
    }

}
