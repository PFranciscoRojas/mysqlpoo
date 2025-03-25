package com.example.testucc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double salario;  // Cambié de 'double' a 'Double' para permitir valores nulos

    public Empleado() {
    }

    public Empleado(String nombre, Double salario) {  // Cambié de 'double' a 'Double'
        this.nombre = nombre;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSalario() {  // Cambié el tipo de 'double' a 'Double'
        return salario;
    }

    public void setSalario(Double salario) {  // Cambié de 'double' a 'Double'
        this.salario = salario;
    }
}
