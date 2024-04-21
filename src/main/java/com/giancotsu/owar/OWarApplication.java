package com.giancotsu.owar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OWarApplication {

	public static void main(String[] args) {
		SpringApplication.run(OWarApplication.class, args);
	}

}
