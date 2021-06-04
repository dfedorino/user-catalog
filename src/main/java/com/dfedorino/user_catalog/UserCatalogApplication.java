package com.dfedorino.user_catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dfedorino.user_catalog"})
public class UserCatalogApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserCatalogApplication.class, args);
	}
}
