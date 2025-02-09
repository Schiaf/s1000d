package fr.schiaf.s1000d.model.dmodule;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ElementXML {
    String private_id;
    String name;
    ElementType type;
    List<ElementXML> attributes;
    List<ElementXML> children;

    private static final String HTML_DIV = "div";

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

    public String toHtml() {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        Element div = body.appendElement(HTML_DIV);
        this.appendChildrenToElement(div);
        return doc.toString();
    }

    protected void appendChildrenToElement(Element tag) {
        for (ElementXML child : this.getChildren()) {
            String childHtml = child.toHtml();
            Document childDoc = Jsoup.parse(childHtml);
            for (Element element : childDoc.body().children()) {
                tag.appendChild(element);
            }
        }
    }

    public String getAttribute(String name) {
        for (ElementXML attribute : getAttributes()) {
            if (attribute.getName().equals(name)) {
                return attribute.getChildren().get(0).toHtml();
            }
        }
        return null;
    }

}
