package com.example.goldprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoldPricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldPricesApplication.class, args);
	}
}
