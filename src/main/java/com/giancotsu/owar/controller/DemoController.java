package com.giancotsu.owar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/user")
    public String getDemoUser() {

        return "demo user";
    }

    @GetMapping("/admin")
    public String getDemoAdmin() {
        return "demo admin";
    }
}
