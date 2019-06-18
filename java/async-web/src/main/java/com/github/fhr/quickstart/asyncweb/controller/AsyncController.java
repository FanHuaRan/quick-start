package com.github.fhr.quickstart.asyncweb.controller;

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
@RequestMapping("/async")
public class AsyncController {

    @RequestMapping("/hello")
    @ResponseBody
    public DeferredResult<ResponseEntity<?>> hello(@RequestParam("name") String name){
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<ResponseEntity<?>>();
        return output;
    }

}
