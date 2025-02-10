package fr.schiaf.s1000d.model.dmodule.generic;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
public class Text extends ElementXML {

    Text() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("");
        this.setType(ElementType.TEXT);
        this.setAttributes(null);
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
       return this.getName();
    }

}
