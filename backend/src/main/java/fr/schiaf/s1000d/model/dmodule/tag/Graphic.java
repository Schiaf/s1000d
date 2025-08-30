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
public class Graphic extends ElementXML {

    Graphic() {
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
        Element img = body.appendElement(HTML_IMG).addClass(this.getName());
        // get the dmCode from descendant 
        String boardno = this.getAttribute("infoEntityIdent");
        //get the image file from the boardno
        String src = this.getDataModule().getEntities().getOrDefault(boardno, boardno + ".SVG");
        img.attr("src", src.replaceAll("\"", "").replaceAll("(?i)\\.CGM$", ".SVG"));
        img.attr("onerror", "this.onerror=null;this.src='NotSupported.png';");
        List<String> usedAttributes = Arrays.asList("infoEntityIdent");
        this.appendChildrenToElement(img, usedAttributes);

        return doc.toString();
    }

}
