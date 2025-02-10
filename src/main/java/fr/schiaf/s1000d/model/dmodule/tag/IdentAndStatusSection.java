package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Component
@Scope("prototype")
public class IdentAndStatusSection extends ElementXML {
    private static final String HTML_H1= "h1";
    private static final String HTML_DIV = "div";

    private static final String S1000D_STATUS= "STATUS";



    IdentAndStatusSection() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("identAndStatusSection");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        body.appendElement(HTML_H1).text(S1000D_STATUS);
        Element div = body.appendElement(HTML_DIV);
        this.appendChildrenToElement(div);
        return doc.toString();
    }

}
