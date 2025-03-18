package com.example.testucc;

import org.springframework.stereotype.Service;

@Service
public class SaludoService {
    public String saludar(String nombre){
        return "hola, mi nombre es: "+nombre;
    }
}
