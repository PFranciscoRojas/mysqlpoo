package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable String id, @RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable String id){
        empleadoRepository.deleteById(Long.parseLong(id));;
    }
    @GetMapping("/search")
    public List<Empleado> buscarEmpleadoPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }
    
    @GetMapping("/sort")
    public List<Empleado> ordenarEmpleadosPorSalario() {
        return empleadoRepository.findAllByOrderBySalarioDesc();
    }
    @PutMapping("batch/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        
        
        return entity;
    }

    
    @PatchMapping("/{id}")
    public void actualizarEmpleadoParcial(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        empleadoRepository.findById(id).ifPresent(empleado -> {

            if (empleadoActualizado.getNombre() != null) {
                empleado.setNombre(empleadoActualizado.getNombre());
            }
            if (empleadoActualizado.getSalario() != 0.0) {
                empleado.setSalario(empleadoActualizado.getSalario());
            }

            empleadoRepository.save(empleado);
        });
    }
    @PutMapping("/batch")
    public List<Empleado> actualizarVarios(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }





    
    
    
}
