package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.Element;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Component
public class Dmodule extends Element {

    Dmodule() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("dmodule");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<Element>());
        this.setChildren(new LinkedList<Element>());
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>");
        sb.append(this.getName());
        sb.append("</title>");
        sb.append("</head>");
        sb.append("<body>");
        for (Element child : this.getChildren()) {
            sb.append(child.toHtml());
        }
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    @Override
    public String toS1000DXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<!DOCTYPE dmodule []>");
        sb.append("<dmodule");
        if(this.getAttributes() != null) {
            for (Element attribute : this.getAttributes()) {
                sb.append(" ");
                sb.append(attribute.getChildren().getFirst().toS1000DXml());
            }
        }
        sb.append(">");
        for (Element child : this.getChildren()) {
            sb.append(child.toS1000DXml());
        }
        sb.append("</dmodule>");
        return sb.toString();
    }

}
