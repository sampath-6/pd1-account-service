package com.sam.pd1.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableCosmosRepositories(basePackages = "com.sam.pd1.account.repository")
public class CosmosConfig {
	
	@Value("${az-cosmosdb-uri}")
	private String uri;
	
	@PostConstruct
	public void test() {
	    System.out.println("URI = " + uri);
	}
}