package com.example.testucc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/empleados")
public class EmpleadoController {


    @Autowired
    private EmpleadoRepository empleadoRepository;

   
    @GetMapping
    public List<empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @PostMapping
    public empleado guardarTodo(@RequestBody empleado Empleado) {
        return empleadoRepository.save(Empleado);
    }

    @GetMapping("/search")
    public List<empleado> obtenerPorNombre(@RequestParam String nombre) {
    return empleadoRepository.findByNombre(nombre);
    }

    @GetMapping("/salario")
    public List<empleado> obtenerPorSalario(@RequestParam double salario) {
        return empleadoRepository.findBySalarioGreaterThanEqual(salario);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleadoRepository.delete(empleado);
    }

    @PutMapping("/{id}")
    public empleado actualizarEmpleado(@PathVariable Long id, @RequestBody empleado empleadoActualizado) {
        empleado Empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Empleado.setNombre(empleadoActualizado.getNombre());
        Empleado.setSalario(empleadoActualizado.getSalario());
        return empleadoRepository.save(Empleado);
    }

    @PatchMapping("/{id}")
    public empleado actualizarEmpleadoParcial(@PathVariable Long id, @RequestBody empleado empleadoParcial) {

        empleado Empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

         if (empleadoParcial.getNombre() != null) {
        Empleado.setNombre(empleadoParcial.getNombre());
            }
        if (empleadoParcial.getSalario() != 0) {
        Empleado.setSalario(empleadoParcial.getSalario());
            }
        return empleadoRepository.save(Empleado);
    }

     @PutMapping("/batch")
    public List<empleado> actualizarEmpleadosBatch(@RequestBody List<empleado> empleadosActualizados) {
        List<empleado> empleadosGuardados = new ArrayList<>();
        
        for (empleado empleadoActualizado : empleadosActualizados) {
            empleado empleadoExistente = empleadoRepository.findById(empleadoActualizado.getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            if (empleadoActualizado.getNombre() != null) {
                empleadoExistente.setNombre(empleadoActualizado.getNombre());
            }
            if (empleadoActualizado.getSalario() != 0) {
                empleadoExistente.setSalario(empleadoActualizado.getSalario());
            }
            empleadosGuardados.add(empleadoRepository.save(empleadoExistente));
        }
        
        return empleadosGuardados;
    }

    @GetMapping("/sort")
    public List<empleado> ordernarEmpleadosPorSalario() {
        return empleadoRepository.findByOrderBySalarioDesc();
    }
    
}
