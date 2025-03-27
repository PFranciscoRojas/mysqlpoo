package com.example.testucc;

import org.springframework.stereotype.Service;

@Service
public class SaludoService {
    public String saludar(String nombre){
        return "hola, tirbuon tiburon tirin, tiburon tiburon, tirin tirin tiburon a la vista bañista "+nombre;
    }
}
