package com.fhr.webflux.restapi.service.impl;

import com.fhr.webflux.restapi.domain.User;
import com.fhr.webflux.restapi.service.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Huaran Fan on 2018/6/19
 *
 * @description:
 */
@Service
public class UserServiceImpl implements com.fhr.webflux.restapi.service.UserService {

	private final Map<String, User> data = new ConcurrentHashMap<>();

	@Override
	public Flux<User> list() {
		return Flux.fromIterable(this.data.values());
	}

	@Override
	public Flux<User> getById(final Flux<String> ids) {
		return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
	}

	@Override
	public Mono<User> getById(final String id) {
		return Mono.justOrEmpty(this.data.get(id))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException()));
	}

	@Override
	public Mono<User> createOrUpdate(final User user) {
		this.data.put(user.getId(), user);
		return Mono.just(user);
	}

	@Override
	public Mono<User> delete(final String id) {
		return Mono.justOrEmpty(this.data.remove(id));
	}

}
