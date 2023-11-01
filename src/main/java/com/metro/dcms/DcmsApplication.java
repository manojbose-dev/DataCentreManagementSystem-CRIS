package com.metro.dcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class DcmsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DcmsApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(DcmsApplication.class);
	}

}
