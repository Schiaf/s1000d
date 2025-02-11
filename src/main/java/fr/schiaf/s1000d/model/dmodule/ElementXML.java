package fr.schiaf.s1000d.model.dmodule;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import fr.schiaf.s1000d.model.dmodule.generic.NotImplemented;
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

    protected static final String HTML_DIV = "div";
    protected static final String HTML_SPAN = "span";
    protected static final String HTML_P = "p";
    protected static final String HTML_LI = "li";
    protected static final String HTML_UL = "ul";
    protected static final String HTML_OL = "ol";
    protected static final String DASH = "-";

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
        List<String> usedAttributes = Arrays.asList("");
        Element span2 = this.addMissingAttribute(usedAttributes);
        if (span2.childrenSize() != 0) {
            body.appendChild(span2);
        }
        return doc.toString();
    }

    protected void appendChildrenToElement(Element tag) {
        for (ElementXML child : this.getChildren()) {
            String childHtml = child.toHtml();
            Document childDoc = Jsoup.parse(childHtml);
            for (Node element : childDoc.body().childNodes()) {
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

    public Element addMissingAttribute(List<String> usedAttributes) {
        Document doc = Document.createShell("");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        Element body = doc.body();
        Element span2 = body.appendElement(HTML_SPAN);
        for (ElementXML attr : this.getAttributes()) {
            //if not implemented, add a span with the name of the attribute
            if (attr instanceof NotImplemented) {
                Document childDoc = Jsoup.parse(    attr.toHtml());
                for (Node element : childDoc.body().childNodes()) {
                    span2.appendChild(element);
                }
            } else {
                // if the attribute is not in the usedAttributes array, add it to the span
                if (!usedAttributes.contains(attr.getName())){
                    StringBuilder attribs = new StringBuilder();
                    attribs.append(this.getAttribute(attr.getName()));
                    attribs.append(attr.toHtml());
                    span2.text(attribs.toString());
                }
            }
        }
        return span2;
    }

}
