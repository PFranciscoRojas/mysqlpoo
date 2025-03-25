package com.example.testucc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        empleado.setNombre(empleadoActualizado.getNombre());
        empleado.setSalario(empleadoActualizado.getSalario());
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Empleado> buscarEmpleadoPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @PatchMapping("/{id}")
    public void actualizarEmpleadoParcial(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        empleadoRepository.findById(id).ifPresent(empleado -> {
            if (empleadoActualizado.getNombre() != null) {
                empleado.setNombre(empleadoActualizado.getNombre());
            }
            if (empleadoActualizado.getSalario() != null) {
                empleado.setSalario(empleadoActualizado.getSalario());
            }
            empleadoRepository.save(empleado);
        });
    }

 
    @GetMapping("/sort")
    public List<Empleado> obtenerEmpleadosOrdenados(@RequestParam String orden) {
        if ("salariosdesc".equalsIgnoreCase(orden)) {
            return empleadoRepository.findAllByOrderBySalarioDesc();
        } else {
            throw new IllegalArgumentException("Parámetro de orden no válido: " + orden);
        }
    }

    
    @PutMapping("/batch")
    public List<Empleado> actualizarSalarios(@RequestBody List<Empleado> empleadosActualizados) {
        return empleadosActualizados.stream().map(empleadoActualizado -> {
            Empleado empleado = empleadoRepository.findById(empleadoActualizado.getId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + empleadoActualizado.getId()));
            empleado.setSalario(empleadoActualizado.getSalario());
            return empleadoRepository.save(empleado);
        }).toList();
    }
}
