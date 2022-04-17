package com.gw.job.sample.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HelloWorldController {

    public String helloWorld() {
        return "Hello World!";
    }
}
