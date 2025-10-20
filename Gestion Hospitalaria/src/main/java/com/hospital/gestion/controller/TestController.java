package com.hospital.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "¡La aplicación está funcionando correctamente!";
    }
    
    @GetMapping("/")
    public String home() {
        return "simple";
    }
}
