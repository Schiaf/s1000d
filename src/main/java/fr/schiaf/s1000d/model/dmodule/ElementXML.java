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
    ElementXML parent;
    DataModule dataModule;
    List<ElementXML> attributes;
    List<ElementXML> children;

    protected static final String HTML_H1= "h1";
    protected static final String HTML_H2 = "h2";
    protected static final String HTML_H3 = "h3";
    protected static final String HTML_H4 = "h4";
    protected static final String HTML_H5 = "h5";
    protected static final String HTML_H6 = "h6";
    protected static final String HTML_HREF = "href";
    protected static final String HTML_HR = "hr";
    protected static final String HTML_A = "a";
    protected static final String HTML_DIV = "div";
    protected static final String HTML_IMG = "img";
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
        Element div = body.appendElement(HTML_DIV).addClass(name);
        String id = this.getAttribute("id");
        if (id != null && !id.isEmpty()) {  
            div.attr("id", id);
        }
        List<String> usedAttributes = Arrays.asList("id");
        this.appendChildrenToElement(div, usedAttributes);
        return doc.toString();
    }

    protected ElementXML getNextSibling() {
        ElementXML parent = this.getParent();
        List<ElementXML> children = parent.getChildren();
        int index = children.indexOf(this);
        if (index < children.size() - 1) {
            return children.get(index + 1);
        }
        return null;
    }

    protected void appendChildrenToElement(Element tag, List<String> usedAttributes, List<String> usedTags, String... classes) {
        Element span2 = this.addMissingAttribute(usedAttributes).addClass("nottreated");
        if (span2.childNodeSize() != 0) {
            tag.insertChildren(0, span2);
        }
        for (ElementXML child : this.getChildren()) {
            if (!usedTags.contains(child.getName())) {
                String childHtml = child.toHtml();
                Document childDoc = Jsoup.parse(childHtml);
                for (Node element : childDoc.body().childNodes()) {
                    // if element is a tag, add the classes
                    if (element instanceof Element) {
                        Element elementElement = (Element) element;
                        for (String clazz : classes) {
                            elementElement.addClass(clazz);
                        }
                    }
                    tag.appendChild(element);
                }
            }
        }
    }

    protected void appendChildrenToElement(Element tag, List<String> usedAttributes, String... classes) {
        List<String> defaultUsedTags = Arrays.asList(""); // Default value for usedTags
        appendChildrenToElement(tag, usedAttributes, defaultUsedTags, classes);
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
                    attribs.append("Not treated: ");
                    attribs.append(attr.toHtml());
                    attribs.append(": ");
                    attribs.append(this.getAttribute(attr.getName()));
                    attribs.append("; ");
                    span2.appendText(attribs.toString());
                }
            }
        }
        return span2;
    }

    public ElementXML getElementXMLbyID(String id) {
        if (this.getAttribute("id") != null && this.getAttribute("id").equals(id)) {
            return this;
        }
        for (ElementXML child : this.getChildren()) {
            ElementXML result = child.getElementXMLbyID(id);
            if (result != null) {
                return result;
            }
        }   
        return null;
    }

    protected ElementXML getRootElementXML() {
        if (this.getParent() == null) {
            return this;
        }
        return this.getParent().getRootElementXML();
    }
}
