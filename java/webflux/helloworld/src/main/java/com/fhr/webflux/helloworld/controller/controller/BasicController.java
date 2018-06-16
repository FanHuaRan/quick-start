package com.fhr.webflux.helloworld.controller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by Fan Huaran on 2018/6/16
 *
 * @description:
 */
@RestController
public class BasicController {

	@GetMapping("/helloworld")
	public Mono<String> sayHelloWorld() {
		return Mono.just("Hello world");
	}

}
