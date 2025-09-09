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
public class Para extends ElementXML {

    Para() {
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
        //if parent is a proceduralStep or a levelledPara
        if (this.getParent() instanceof ProceduralStep || this.getParent() instanceof LevelledPara) {
            //if parent have a title child, do not create a p tag
            if (!this.getParent().getChildren().stream().anyMatch(child -> child instanceof Title)) {
                int level = 0;
                ElementXML parent = this.getParent();
                if (parent instanceof ProceduralStep || parent instanceof LevelledPara) {
                    while (parent instanceof ProceduralStep || parent instanceof LevelledPara) {
                        level++;
                        parent = parent.getParent();
                    }
                }else if (parent instanceof Figure) {
                    level = 6;
                }
                Element hx = null;
                switch (level) {
                    case 0:
                        hx = body.appendElement(HTML_SPAN);
                        break;
                    case 1:
                        hx = body.appendElement(HTML_H2);
                        break;
                    case 2:
                        hx = body.appendElement(HTML_H3);
                        break;
                    case 3:
                        hx = body.appendElement(HTML_H4);
                        break;
                    case 4:
                        hx = body.appendElement(HTML_H5);
                        break;
                    case 5:
                        hx = body.appendElement(HTML_H6);
                        break;
                    default:
                        hx = body.appendElement(HTML_SPAN);
                        break;
                }
                List<String> usedAttributes = Arrays.asList("");
                this.appendChildrenToElement(hx, usedAttributes);
            }else{
                Element p = body.appendElement(HTML_P);
                p.attributes().put("id", this.getAttribute("id"));
                List<String> usedAttributes = Arrays.asList("id");
                this.appendChildrenToElement(p, usedAttributes);
            }
        }else{
            Element p = body.appendElement(HTML_P);
            p.attributes().put("id", this.getAttribute("id"));
            List<String> usedAttributes = Arrays.asList("id");
            this.appendChildrenToElement(p, usedAttributes);
        }
        return doc.toString();
    }

}
