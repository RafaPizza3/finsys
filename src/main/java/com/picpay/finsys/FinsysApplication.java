package com.picpay.finsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FinsysApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinsysApplication.class, args);
	}
}
