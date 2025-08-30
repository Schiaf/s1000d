package fr.schiaf.s1000d.model.dmodule;


public interface ElementFactory {
    ElementXML createElement(String type, ElementType elementType, ElementXML parent, DataModule dataModule);
}