package com.fhr.webflux.routes.config;

import com.fhr.webflux.routes.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by Huaran Fan on 2018/6/21
 *
 * @description:
 */
@Configuration
public class RouteConfig {

	private UserHandler userHandler;

	@Autowired
	public RouteConfig(UserHandler userHandler) {
		this.userHandler = userHandler;
	}

	@Bean
	public RouterFunction<?> routerFunction() {
		return route(GET("/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::listUsers)
				.and(route(GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser));
	}

}