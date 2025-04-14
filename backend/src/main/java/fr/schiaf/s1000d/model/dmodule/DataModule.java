package fr.schiaf.s1000d.model.dmodule;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataModule {
    String xmlDeclaration;
    String doctype;
    Map<String, String> entities;
    ElementXML root;


}
