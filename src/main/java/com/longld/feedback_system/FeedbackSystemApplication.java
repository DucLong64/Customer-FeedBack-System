package com.longld.feedback_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class FeedbackSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackSystemApplication.class, args);
	}

}
