package fr.schiaf.s1000d.model.dmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.tag.*;
import fr.schiaf.s1000d.model.dmodule.attribute.*;
import fr.schiaf.s1000d.model.dmodule.generic.*;

@Component
public class ElementFactoryImpl implements ElementFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public ElementXML createElement(String type) {
        switch (type) {
            case "dmodule":
                return applicationContext.getBean(Dmodule.class);
            case "identAndStatusSection":
                return applicationContext.getBean(IdentAndStatusSection.class);
            case "dmAddress":
                return applicationContext.getBean(DmAddress.class);
            case "dmIdent":
                return applicationContext.getBean(DmIdent.class);
            case "dmCode":
                return applicationContext.getBean(DmCode.class);
            case "modelIdentCode":
                return applicationContext.getBean(ModelIdentCode.class);
            case "text":
                return applicationContext.getBean(Text.class);
            default:
                throw new IllegalArgumentException("Type d'élément inconnu: " + type);
        }
    }
}