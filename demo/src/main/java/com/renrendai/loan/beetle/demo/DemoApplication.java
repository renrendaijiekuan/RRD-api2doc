package com.renrendai.loan.beetle.demo;

import com.renrendai.loan.beetle.commons.api2doc.config.EnableApi2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableApi2Doc
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
