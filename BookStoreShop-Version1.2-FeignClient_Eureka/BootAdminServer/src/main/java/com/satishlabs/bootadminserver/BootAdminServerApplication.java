package com.satishlabs.bootadminserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class BootAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootAdminServerApplication.class, args);
	}

}
