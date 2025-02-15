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
public class ReasonForUpdate extends ElementXML {

    private static final String S1000D_TITLE = "Reason for Update ";
    
    ReasonForUpdate() {
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
        div.attributes().put("id", this.getAttribute("id"));
        List<String> usedAttributes = Arrays.asList("updateReasonType", "id");
        StringBuilder rfu = new StringBuilder();
        rfu.append(S1000D_TITLE);
        String urt = this.getAttribute("updateReasonType");
        if (urt != null && !urt.isEmpty()) {
            rfu.append("(");
            rfu.append(urt);
            rfu.append(")");
        }
        rfu.append(": ");
        Element span = div.appendElement(HTML_SPAN).text(rfu.toString());
        span.addClass("bold");
        this.appendChildrenToElement(div, usedAttributes, "indented");
        return doc.toString();
    }

}
