package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
        String refid = Optional.ofNullable(this.getAttribute("internalRefId")).orElse("");
        String type = Optional.ofNullable(this.getAttribute("internalRefTargetType")).orElse("");
        String content = "click here";
        ElementXML elem = this.getRootElementXML().getElementXMLbyID(refid);
        switch (type) {
            case "irtt01":
                content = "Figure ";
                if (elem != null) {
                    //get the first child of the paragraph
                    ElementXML firstChild = elem.getChildren().get(0);
                    if (firstChild != null && firstChild instanceof Title) {
                        content += firstChild.getChildren().get(0).toHtml();
                    }
                }
                break;
            case "irtt02":

                break;
            case "irtt03":
            
                break;
            case "irtt04":
                if (elem != null) {
                    //get the text content of first child of the supplyDescr
                    ElementXML firstChild = elem.getChildren().get(0);
                    if (firstChild != null && firstChild instanceof Name) {
                        content = firstChild.getChildren().get(0).toHtml();
                    }
                }
                break;
            case "irtt05":
                
                break;
            case "irtt06":
                
                break;
            case "irtt07":
                content = "Paragraph ";
                if (elem != null) {
                    //get the first child of the paragraph
                    ElementXML firstChild = elem.getChildren().get(0);
                    if (firstChild != null && firstChild instanceof Title) {
                        content += firstChild.getChildren().get(0).toHtml();
                    }
                }
                break;
            default:
                content = "Broken Reference";
                break;
        }
        anchor.attr(HTML_HREF, "#" + refid).text(content);
        List<String> usedAttributes = Arrays.asList("internalRefId", "internalRefTargetType");
        this.appendChildrenToElement(anchor, usedAttributes);

        return doc.toString();
    }

}
