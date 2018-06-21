package com.fhr.webflux.restapi.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Huaran Fan on 2018/6/16
 *
 * @description:
 */
@SpringBootApplication
@ComponentScan("com.fhr.webflux.restapi")
@EnableAutoConfiguration
public class RestApiWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiWebfluxApplication.class, args);
	}
}
