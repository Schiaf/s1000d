package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.Element;
import fr.schiaf.s1000d.model.dmodule.ElementType;

@Component
public class IdentAndStatusSection extends Element {
    private static final String HTML_STATUS_HEADER = "<h1>STATUS</h1>";
    private static final String HTML_DIV_OPEN = "<div>";
    private static final String HTML_DIV_CLOSE = "</div>";
    private static final String XML_TAG_START = "<identAndStatusSection";
    private static final String XML_TAG_END = "</identAndStatusSection>";

    IdentAndStatusSection() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("identAndStatusSection");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<Element>());
        this.setChildren(new LinkedList<Element>());
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder(100); // Pre-allocate buffer
        sb.append(HTML_STATUS_HEADER)
          .append(HTML_DIV_OPEN);
        
        List<Element> children = getChildren();
        if (children != null) {
            for (Element child : children) {
                if (child != null) {
                    sb.append(child.toHtml());
                }
            }
        }
        
        sb.append(HTML_DIV_CLOSE);
        return sb.toString();
    }

    @Override
    public String toS1000DXml() {
        StringBuilder sb = new StringBuilder(200); // Pre-allocate buffer
        sb.append(XML_TAG_START);
        
        List<Element> attributes = getAttributes();
        if (attributes != null) {
            for (Element attribute : attributes) {
                if (attribute != null && attribute.getChildren() != null && !attribute.getChildren().isEmpty()) {
                    sb.append(" ");
                    sb.append(attribute.getChildren().get(0).toS1000DXml());
                }
            }
        }
        
        sb.append(">");
        
        List<Element> children = getChildren();
        if (children != null) {
            for (Element child : children) {
                if (child != null) {
                    sb.append(child.toS1000DXml());
                }
            }
        }
        
        sb.append(XML_TAG_END);
        return sb.toString();
    }
}
