package fr.schiaf.s1000d.model.dmodule.attribute;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
public class SubSystemCode extends ElementXML {

    SubSystemCode() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("subSystemCode");
        this.setType(ElementType.ATTRIBUTE);
        this.setAttributes(null);
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
       return this.getName();
    }

}
