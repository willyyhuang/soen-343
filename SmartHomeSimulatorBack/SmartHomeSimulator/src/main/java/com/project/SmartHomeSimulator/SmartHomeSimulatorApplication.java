package com.project.SmartHomeSimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SmartHomeSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartHomeSimulatorApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/simulation/*").allowedOrigins("*");
				registry.addMapping("/api/v1/user/*").allowedOrigins("*");
			}
		};
	}
}
