package fr.schiaf.s1000d.model.dmodule.tag;

import java.util.LinkedList;
import java.util.UUID;

import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.ElementType;
import fr.schiaf.s1000d.model.dmodule.ElementXML;

@Component
public class DmIdent extends ElementXML {

    DmIdent() {
        //generate ramdom unique id based on uuid
        this.setPrivate_id(UUID.randomUUID().toString());
        this.setName("dmIdent");
        this.setType(ElementType.TAG);
        this.setAttributes(new LinkedList<ElementXML>());
        this.setChildren(new LinkedList<ElementXML>());
    }

    @Override
    public String toHtml() {
       return "";
    }

}
