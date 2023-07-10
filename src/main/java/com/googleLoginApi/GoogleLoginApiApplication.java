package com.googleLoginApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GoogleLoginApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleLoginApiApplication.class, args);
	}

}
