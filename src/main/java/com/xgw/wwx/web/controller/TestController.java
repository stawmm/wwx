package com.xgw.wwx.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestController {


    @GetMapping(value = "/say")
    private String say() {
        return "Hello!";
    }
}
