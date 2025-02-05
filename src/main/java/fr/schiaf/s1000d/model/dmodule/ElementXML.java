package fr.schiaf.s1000d.model.dmodule;

import java.util.List;

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

    public abstract String toHtml();
    public abstract String toS1000DXml();


}
