package com.avitam.fantasy11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
		{
				"com.avitam.fantasy11"
		})
public class Fantasy11Application {

	public static void main(String[] args) {
		SpringApplication.run(Fantasy11Application.class, args);
	}

}
