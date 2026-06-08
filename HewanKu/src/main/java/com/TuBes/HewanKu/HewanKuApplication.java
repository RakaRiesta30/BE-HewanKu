package com.TuBes.HewanKu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HewanKuApplication {

	public static void main(String[] args) {
		SpringApplication.run(HewanKuApplication.class, args);
	}

}
