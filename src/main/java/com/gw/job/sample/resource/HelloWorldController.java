package com.gw.job.sample.resource;

import org.springframework.stereotype.Controller;

@Controller
public class HelloWorldController {

    public String helloWorld() {
        return "Hello World!";
    }
}
