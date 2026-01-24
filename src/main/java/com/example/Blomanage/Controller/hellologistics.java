package com.example.Blomanage.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellologistics {
    @GetMapping("/")
    public String myController(){
        return "hello this is my logistic project with AI analysis";
    }
}
