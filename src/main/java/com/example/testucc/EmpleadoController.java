package com.example.testucc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @GetMapping("/search/{nombre}")
    public List<Empleado> buscarPorNombre(@PathVariable String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empleado> actualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    empleado.setNombre((String) value);
                    break;
                case "salario":
                    empleado.setSalario(Double.parseDouble(value.toString()));
                    break;
            }
        });

        return ResponseEntity.ok(empleadoRepository.save(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado nuevoEmpleado) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpleado.getNombre());
                    empleado.setSalario(nuevoEmpleado.getSalario());
                    return ResponseEntity.ok(empleadoRepository.save(empleado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/salarios")
    public List<Double> obtenerSalariosOrdenados() {
        return empleadoRepository.findAllByOrderBySalarioDesc()
                .stream()
                .map(Empleado::getSalario)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long id) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleadoRepository.delete(empleado);
                    return ResponseEntity.ok("Empleado eliminado exitosamente.");
                })
                .orElse(ResponseEntity.status(404).body("Empleado no encontrado."));
    }
}
