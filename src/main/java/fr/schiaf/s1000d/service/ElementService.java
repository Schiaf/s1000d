package fr.schiaf.s1000d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.schiaf.s1000d.model.dmodule.ElementXML;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;

@Service
public class ElementService {

    @Autowired
    private ElementFactory elementFactory;

    public void processElements() {
        ElementXML dmodule = elementFactory.createElement("dmodule");
        ElementXML identAndStatusSection = elementFactory.createElement("identAndStatusSection");
        ElementXML dmAddress = elementFactory.createElement("dmAddress");
        ElementXML dmIdent = elementFactory.createElement("dmIdent");
        ElementXML dmCode = elementFactory.createElement("dmCode");
        ElementXML modelIdentCode = elementFactory.createElement("modelIdentCode");
        ElementXML modelIdentCodeText = elementFactory.createElement("text");
        modelIdentCodeText.setName("BRAKE");
        modelIdentCode.getChildren().add(modelIdentCodeText);
        dmCode.getAttributes().add(modelIdentCode);
        dmIdent.getChildren().add(dmCode);
        dmAddress.getChildren().add(dmIdent);
        identAndStatusSection.getChildren().add(dmAddress);
        dmodule.getChildren().add(identAndStatusSection);
        System.out.println(dmodule.toS1000DXml());
        System.out.println(dmodule.toHtml());
    }
}