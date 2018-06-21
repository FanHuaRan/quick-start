package com.fhr.webflux.restapi.controller;

import com.fhr.webflux.restapi.domain.User;
import com.fhr.webflux.restapi.service.ResourceNotFoundException;
import com.fhr.webflux.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Created by Huaran Fan on 2018/6/19
 *
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "resource is not found")
	@ExceptionHandler(ResourceNotFoundException.class)
	public void notFound(){

	}

	@GetMapping("")
	public Flux<User> list() {
		return this.userService.list();
	}

	@GetMapping("/{id}")
	public Mono<User>getById(@PathVariable("id") final String id) {
		return this.userService.getById(id);
	}

	@PostMapping("")
	public Mono<User> create(@RequestBody final User user) {
		return this.userService.createOrUpdate(user);
	}

	@PutMapping("/{id}")
	public Mono<User>  update(@PathVariable("id") final String id, @RequestBody final User user) {
		Objects.requireNonNull(user);
		user.setId(id);
		return this.userService.createOrUpdate(user);
	}

	@DeleteMapping("/{id}")
	public Mono<User>  delete(@PathVariable("id") final String id) {
		return this.userService.delete(id);
	}
}
