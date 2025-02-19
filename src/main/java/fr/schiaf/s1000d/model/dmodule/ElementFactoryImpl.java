package fr.schiaf.s1000d.model.dmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import fr.schiaf.s1000d.model.dmodule.generic.NotImplemented;

@Component
public class ElementFactoryImpl implements ElementFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public ElementXML createElement(String type, ElementType elementType, ElementXML parent, DataModule dataModule) {
        ElementXML elt = null;
        Class<?> clazz = null;
        String[] basePackages = { "fr.schiaf.s1000d.model.dmodule.tag", "fr.schiaf.s1000d.model.dmodule.attribute",
                "fr.schiaf.s1000d.model.dmodule.generic" };

        for (String basePackage : basePackages) {
            try {
                String className = basePackage + "." + type.substring(0, 1).toUpperCase() + type.substring(1);
                clazz = Class.forName(className);
                break; // Exit loop if class is found
            } catch (ClassNotFoundException e) {
                // Continue to the next package
            }
        }

        if (clazz != null) {
            elt = (ElementXML) applicationContext.getBean(clazz);
        } else {
            elt = applicationContext.getBean(NotImplemented.class);
        }

        elt.setName(type);
        elt.setType(elementType);
        elt.setParent(parent);
        elt.setDataModule(dataModule);
        return elt;
    }
}