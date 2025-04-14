package fr.schiaf.s1000d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.schiaf.s1000d.service.ElementService;


@SpringBootApplication
public class S1000dApplication implements CommandLineRunner {

	@Autowired
    private ElementService elementService;
	
	public static void main(String[] args) {
		SpringApplication.run(S1000dApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//elementService.processElements();
		elementService.processElementsFromFile("src/main/resources/DMC-BRAKE-AAA-DA1-00-00-00AA-041A-A_004-00_EN-US.XML");
	}

}
