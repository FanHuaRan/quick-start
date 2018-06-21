package com.fhr.webflux.routes.handler;

import com.fhr.webflux.routes.domain.User;
import com.fhr.webflux.routes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Created by Huaran Fan on 2018/6/19
 *
 * @description:
 */
@Component
public class UserHandler {
	private final UserService userService;

	@Autowired
	public UserHandler(UserService userService) {
		this.userService = userService;
	}

	public Mono<ServerResponse> listUsers(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(userService.list(), User.class);
	}

	public Mono<ServerResponse> getUser(ServerRequest request) {
		return userService.getById(request.pathVariable("id"))
				.flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(user),User.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
