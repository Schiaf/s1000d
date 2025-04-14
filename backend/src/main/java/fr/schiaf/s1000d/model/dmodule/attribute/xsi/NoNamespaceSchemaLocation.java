package fr.schiaf.s1000d.model.dmodule.attribute.xsi;


import java.util.LinkedList;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
@Scope("prototype")
public class NoNamespaceSchemaLocation extends ElementXML {
    
    NoNamespaceSchemaLocation() {
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setAttributes(null);
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
        return "";
    }

}
