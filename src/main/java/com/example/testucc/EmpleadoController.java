package com.example.testucc;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @GetMapping("/search")
    public Empleado buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }
    

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empleado> actualizarSalario(@PathVariable Long id, @RequestBody Empleado nuevoEmpleado) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);
        if (empleadoExistente.isPresent()) {
            Empleado empleado = empleadoExistente.get();
            empleado.setSalario(nuevoEmpleado.getSalario());
            Empleado empleadoActualizado = empleadoRepository.save(empleado);
            return ResponseEntity.ok(empleadoActualizado);
        }
        return null;
    }

    @PutMapping("/batch")
    public List<Empleado> actualizarEmpleados(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepository.delete(empleado);
    }
}
