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
		Element dmodule1 = elementFactory.createElement("dmodule");
		System.out.println(dmodule1.toS1000DXml());
	}

}
