package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
public class DmAddress extends ElementXML {

    DmAddress() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("dmAddress");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
       return "";
    }

    @Override
    public String toS1000DXml() {
        Document doc = new Document("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element root = doc.appendElement(this.getName());
        List<ElementXML> attributes = getAttributes();
        if (attributes != null) {
            for (ElementXML attribute : attributes) {
                if (attribute != null && attribute.getChildren() != null && !attribute.getChildren().isEmpty()) {
                    root.attr(attribute.getName(), attribute.getChildren().get(0).toS1000DXml());
                }
            }
        }
        for (ElementXML child : this.getChildren()) {
            root.append(child.toS1000DXml());
        }
        return doc.toString();
    }

}
