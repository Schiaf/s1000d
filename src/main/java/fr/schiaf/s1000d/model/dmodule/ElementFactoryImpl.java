package fr.schiaf.s1000d.model.dmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.tag.DmAddress;
import fr.schiaf.s1000d.model.dmodule.tag.Dmodule;
import fr.schiaf.s1000d.model.dmodule.tag.IdentAndStatusSection;

@Component
public class ElementFactoryImpl implements ElementFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Element createElement(String type) {
        switch (type) {
            case "dmodule":
                return applicationContext.getBean(Dmodule.class);
            case "identAndStatusSection":
                return applicationContext.getBean(IdentAndStatusSection.class);
            case "dmAddress":
                return applicationContext.getBean(DmAddress.class);
            default:
                throw new IllegalArgumentException("Type d'élément inconnu: " + type);
        }
    }
}