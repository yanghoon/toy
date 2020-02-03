package com.example.demo;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("function")
@Configuration
public class UppercaseFunction {
	@Bean
	public Function<String, String> uppercase() {
		return value -> value.toUpperCase();
	}
}