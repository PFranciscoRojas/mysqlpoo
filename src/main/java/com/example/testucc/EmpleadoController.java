package com.example.testucc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }
    
    @PostMapping()
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            empleadoRepository.delete(empleado.get());
        } else {

        }
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoNuevo) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);
        if (empleadoExistente.isPresent()) {
            Empleado empleado = empleadoExistente.get();
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setSalario(empleadoNuevo.getSalario());
            return empleadoRepository.save(empleado);
        } else {

        return null;
        }
    }

    @GetMapping("/search")
    public List<Empleado> buscarEmpleadoPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @PatchMapping("/{id}")
    public Empleado actualizarEmpleadoParcial(@PathVariable Long id, @RequestBody Empleado empleadoParcial) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);
        if (empleadoExistente.isPresent()) {
            Empleado empleado = empleadoExistente.get();
            if (empleadoParcial.getNombre() != null) {
                empleado.setNombre(empleadoParcial.getNombre());
            }
            if (empleadoParcial.getSalario() != -1) {
                empleado.setSalario(empleadoParcial.getSalario());
            }
            return empleadoRepository.save(empleado);
        } else {
            return null;
        }
    }

    @PutMapping("/batch")
    public ResponseEntity<List<Empleado>> actualizarEmpleados(@RequestBody List<Empleado> empleados) {
        List<Empleado> empleadosActualizados = empleados.stream().map(emp -> 
            empleadoRepository.findById(emp.getId()).map(empleado -> {
                Optional.ofNullable(emp.getNombre()).ifPresent(empleado::setNombre);
                Optional.ofNullable(emp.getSalario()).ifPresent(empleado::setSalario);
                return empleadoRepository.save(empleado);
            }).orElse(null)
        ).filter(Objects::nonNull).collect(Collectors.toList());

        return empleadosActualizados.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(empleadosActualizados);
    }

    @GetMapping("/sort")
    public List<Empleado> OrdenarEmpleadoPorSalario() {
        return empleadoRepository.findAllByOrderBySalarioDesc();
    }
}
