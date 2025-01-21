package fr.schiaf.s1000d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.schiaf.s1000d.model.dmodule.Element;
import fr.schiaf.s1000d.model.dmodule.ElementFactory;


@SpringBootApplication
public class S1000dApplication implements CommandLineRunner {

	@Autowired
    private ElementFactory elementFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(S1000dApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Element dmodule = elementFactory.createElement("dmodule");
		Element identAndStatusSection = elementFactory.createElement("identAndStatusSection");
		Element dmAddress = elementFactory.createElement("dmAddress");
		identAndStatusSection.getChildren().add(dmAddress);
		dmodule.getChildren().add(identAndStatusSection);
		System.out.println(dmodule.toS1000DXml());
	}

}
