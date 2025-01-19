package fr.schiaf.s1000d.model.dmodule;

import org.springframework.stereotype.Component;

@Component
public class ElementFactoryImpl implements ElementFactory {

    @Override
    public Element createElement(String type) {
        switch (type) {
            case "dmodule":
                return new dmodule();
            // Ajoutez d'autres cas pour d'autres sous-classes d'Element si nécessaire
            default:
                throw new IllegalArgumentException("Type d'élément inconnu: " + type);
        }
    }
}