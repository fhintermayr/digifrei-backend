package de.icp.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DigiFreiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigiFreiApplication.class, args);
	}

}
