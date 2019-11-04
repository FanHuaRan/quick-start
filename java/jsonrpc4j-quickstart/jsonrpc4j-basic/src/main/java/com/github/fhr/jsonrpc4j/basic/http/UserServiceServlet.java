package com.github.fhr.jsonrpc4j.basic.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fhr.jsonrpc4j.service.user.UserService;
import com.github.fhr.jsonrpc4j.service.user.UserServiceImpl;
import com.googlecode.jsonrpc4j.JsonRpcServer;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class UserServiceServlet extends HttpServlet {

    private UserService userService;
    private JsonRpcServer jsonRpcServer;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // do it
        jsonRpcServer.handle(req, resp);
    }

    public void init(ServletConfig config) {
        this.userService = new UserServiceImpl();
        this.jsonRpcServer = new JsonRpcServer(new ObjectMapper(), this.userService, UserService.class);
    }
}
