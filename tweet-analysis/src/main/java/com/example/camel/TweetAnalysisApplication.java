package com.example.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.camel","com.unimelb.sudo.regional"})
public class TweetAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetAnalysisApplication.class, args);
	}

}
