package fr.schiaf.s1000d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.schiaf.s1000d.model.dmodule.Element;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;

@Service
public class ElementService {

    @Autowired
    private ElementFactory elementFactory;

    public void processElements() {
        Element dmodule = elementFactory.createElement("dmodule");
        Element identAndStatusSection = elementFactory.createElement("identAndStatusSection");
        Element dmAddress = elementFactory.createElement("dmAddress");
        identAndStatusSection.getChildren().add(dmAddress);
        dmodule.getChildren().add(identAndStatusSection);
        System.out.println(dmodule.toS1000DXml());
    }
}