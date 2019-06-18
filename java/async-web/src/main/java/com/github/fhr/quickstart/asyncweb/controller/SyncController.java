package com.github.fhr.quickstart.asyncweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Fan Huaran
 * created on 2019/6/17
 * @description
 */
@Controller
@RequestMapping("/sync")
public class SyncController {

    @RequestMapping("/hello")
    public ResponseEntity<?> hello(@RequestParam("name") String name) {
        return new ResponseEntity<Object>("hi " + name, HttpStatus.OK);
    }

}
