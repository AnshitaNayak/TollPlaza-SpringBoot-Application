package com.toll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TollPlazaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TollPlazaServiceApplication.class, args);
	}
}
