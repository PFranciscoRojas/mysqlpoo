package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    public Empleado guardarTodo(Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepository.delete(empleado);
    }
}
