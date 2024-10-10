package com.avitam.fantasy11.suite;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableMongoRepositories("com.avitam.fantasy11.model")
@ComponentScan(
		{
				"com.avitam.fantasy11.tokenGeneration",
				"com.avitam.fantasy11.api",
				"com.avitam.fantasy11.web.controllers",
				"com.avitam.fantasy11.core",
				"com.avitam.fantasy11.qa",
				"com.avitam.fantasy11.validation",
				"com.avitam.fantasy11.mail",
				"com.avitam.fantasy11.listener",
				"com.avitam.fantasy11.model",
		}
)
public class Fantasy11Application {

	public static void main(String[] args) {
		SpringApplication.run(Fantasy11Application.class,args);
	}

	@Bean
	public  ResourceBundleMessageSource messageSource() {
		var source = new ResourceBundleMessageSource();
		source.setBasename("message");
		source.setDefaultEncoding("UTF-8");
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		return sessionLocaleResolver;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}

	@Bean
	JavaMailSenderImpl mailSender() {
		return new JavaMailSenderImpl();
	}

	@Bean
	public Locale locale() {
		return Locale.ENGLISH;
	}

}