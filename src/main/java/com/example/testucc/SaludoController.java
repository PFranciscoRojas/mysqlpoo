package com.example.testucc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SaludoController {
  
    @Autowired
    private SaludoService saludoService;

    @GetMapping("/saludo")
    public String getSaludo(){
       return saludoService.saludar("tomasin");
    }
}
