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
public class InternalRef extends ElementXML {

    InternalRef() {
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
        Element anchor = body.appendElement(HTML_A);
        String refid = this.getAttribute("internalRefId");
        String type = this.getAttribute("internalRefTargetType");
        String content = "click here";
        switch (type) {
            case "irtt01":
                
                break;
            case "irtt02":

                break;
            case "irtt03":
            
                break;
            case "irtt04":
                
                break;
            case "irtt05":
                
                break;
            case "irtt06":
                
                break;
            case "irtt07":
                content = "Paragraph ";
                ElementXML elem = this.getRootElementXML().getElementXMLbyID(refid);
                if (elem != null) {
                    //get the first child of the paragraph
                    ElementXML firstChild = elem.getChildren().get(0);
                    if (firstChild != null && firstChild instanceof Title) {
                        content += firstChild.getChildren().get(0).toHtml();
                    }
                }
                break;
            default:
                break;
        }
        anchor.attr(HTML_HREF, "#" + refid).text(content);
        List<String> usedAttributes = Arrays.asList("internalRefId", "internalRefTargetType");
        this.appendChildrenToElement(anchor, usedAttributes);

        return doc.toString();
    }

}
