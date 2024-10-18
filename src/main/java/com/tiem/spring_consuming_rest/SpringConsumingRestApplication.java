package com.tiem.spring_consuming_rest;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class SpringConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringConsumingRestApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringConsumingRestApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject("http://localhost:8080/api/1", Quote.class);
//			String quote = restTemplate.getForObject("http://localhost:8080/greeting?name=Tun", String.class);
			log.info(quote.toString());
			
			log.info("\nget all quotes");
			List<Quote> quotes = restTemplate.exchange("http://localhost:8080/api", HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<Quote>>() {
					}).getBody();
			quotes.stream().forEach(q -> log.info(q.toString()));
		};
	}

}
