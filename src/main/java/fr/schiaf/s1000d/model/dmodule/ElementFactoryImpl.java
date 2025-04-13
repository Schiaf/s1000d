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
        String basePackagePrefix = "fr.schiaf.s1000d.model.dmodule";
        String[] basePackages = { "tag", "attribute",
                "generic" };
        //if type contains namespace, remove it and keep it in a variable
        String namespace = "";
        if (type.contains(":")) {
            namespace = type.substring(0, type.indexOf(":")) + ".";
            type = type.substring(type.indexOf(":") + 1);
        }

        for (String basePackage : basePackages) {
            try {
                String className = basePackagePrefix + "." + basePackage + "." + namespace + type.substring(0, 1).toUpperCase() + type.substring(1);
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