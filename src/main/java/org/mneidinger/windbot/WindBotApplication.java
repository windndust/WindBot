package org.mneidinger.windbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WindBotApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WindBotApplication.class, args);
	}

}
