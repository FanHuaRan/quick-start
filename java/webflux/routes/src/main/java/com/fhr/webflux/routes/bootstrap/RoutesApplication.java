package com.fhr.webflux.routes.bootstrap;

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
@ComponentScan("com.fhr.webflux.routes")
@EnableAutoConfiguration
public class RoutesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutesApplication.class, args);
	}
}
