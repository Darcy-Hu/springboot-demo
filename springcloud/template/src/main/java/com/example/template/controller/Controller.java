package com.example.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

@RestController
@RequestMapping("/api/template")
public class Controller {

    @Autowired
    TemplateEngine templateEngine;

    @GetMapping("/{message}")
    public String getTemplate(@PathVariable String message){
        String context = templateEngine.process(message,null);
        return context;
    }
}

