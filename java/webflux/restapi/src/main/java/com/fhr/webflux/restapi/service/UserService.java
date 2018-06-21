package com.fhr.webflux.restapi.service;

import com.fhr.webflux.restapi.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Huaran Fan on 2018/6/19
 *
 * @description:
 */
public interface UserService {
	Flux<User> list();

	Flux<User> getById(Flux<String> ids);

	Mono<User> getById(String id);

	Mono<User> createOrUpdate(User user);

	Mono<User> delete(String id);
}
