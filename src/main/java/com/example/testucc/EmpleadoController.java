package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/lista")
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre){
        return empleadoRepository.findByNombre(nombre);
    }

    @GetMapping("/sort")
    public List<Empleado> buscarOrdenadoPorSalario() {
        return empleadoRepository.findAllByOrderBySalarioDesc();
    }


    @PutMapping("/batch")
    public List<Empleado> actualizarEmpleados(@RequestBody List<Empleado> empleados){
        return empleadoRepository.saveAll(empleados);
    }

    @PutMapping("{id}")
    public void actualizarEmpleado(@RequestBody Empleado empleado){
        empleadoRepository.save(empleado);
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepository.delete(empleado);
    }

    @PatchMapping("/{id}")
    public void actualizarSalario(@PathVariable Long id, @RequestBody Empleado empleado){
        Empleado empleadoActual = empleadoRepository.findById(id).get();
        empleadoActual.setSalario(empleado.getSalario());
        empleadoRepository.save(empleadoActual);
    }
    
}   
