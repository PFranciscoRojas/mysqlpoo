package com.example.testucc;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @GetMapping("/search")
    public Empleado buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @GetMapping("/sort")
    public List<Empleado> ordenarPorSalario() {
        return empleadoRepository.findByOrderBySalarioDesc();
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @PatchMapping("/{id}")
    public Empleado actualizarSalario(@PathVariable Long id, @RequestBody Empleado empleado) {
        Optional<Empleado> emp = empleadoRepository.findById(id);
        if (emp.isPresent()) {
            Empleado e = emp.get();
            e.setSalario(empleado.getSalario());
            return empleadoRepository.save(e);
        }
        return null;
    }

    @PutMapping("/batch")
    public List<Empleado> actualizarVarios(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }
}