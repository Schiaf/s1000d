package fr.schiaf.s1000d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class S1000dApplication extends SpringBootServletInitializer {

	/**
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
 */

     public static void main(String[] args) {
        SpringApplication.run(S1000dApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(S1000dApplication.class);
    }
}
