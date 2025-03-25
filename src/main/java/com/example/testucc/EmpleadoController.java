package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Obtener todos los empleados
    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    // Buscar empleados por nombre
    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombreContaining(nombre);
    }

    // Guardar un nuevo empleado
    @PostMapping
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Actualización parcial (PATCH) de un empleado por ID
    @PatchMapping("/{id}")
    public Empleado actualizarParcial(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        return empleadoRepository.findById(id).map(empleado -> {
            if (empleadoActualizado.getSalario() > 0) {
                empleado.setSalario(empleadoActualizado.getSalario());
            }
            if (empleadoActualizado.getNombre() != null) {
                empleado.setNombre(empleadoActualizado.getNombre());
            }
            return empleadoRepository.save(empleado);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    // Actualizar múltiples empleados en batch (PUT)
    @PutMapping("/batch")
    public List<Empleado> actualizarBatch(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    // Obtener empleados ordenados por salario (ejemplo)
    @GetMapping("/sort")
    public List<Empleado> ordenarEmpleadosPorSalario() {
        return empleadoRepository.findAll();
    }

    // Actualizar un empleado por ID (PUT)
    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        return empleadoRepository.findById(id).map(empleado -> {
            empleado.setNombre(empleadoActualizado.getNombre());
            empleado.setSalario(empleadoActualizado.getSalario());
            return empleadoRepository.save(empleado);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    // Eliminar un empleado por ID (DELETE)
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }
}
