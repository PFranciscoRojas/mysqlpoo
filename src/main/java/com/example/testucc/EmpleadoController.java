package com.example.testucc;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id){
        empleadoRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
    @PatchMapping("/{id}")
    public Empleado actualizarParcialmente(@PathVariable Long id, @RequestBody Empleado nuevosDatos) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);

        if (empleadoExistente.isPresent()) {
            Empleado empleado = empleadoExistente.get();

            if (nuevosDatos.getNombre() != null) {
                empleado.setNombre(nuevosDatos.getNombre());
            }
            if (nuevosDatos.getSalario() != null) {
                empleado.setSalario(nuevosDatos.getSalario());
            }

            return empleadoRepository.save(empleado);
        }
        return null;
    }
    @PutMapping("/batch")
    public List<Empleado> actualizarPorNombreOSalario(@RequestBody Empleado nuevosDatos) {
        List<Empleado> empleados = empleadoRepository.findAll()
                .stream()
                .filter(e -> 
                    (nuevosDatos.getNombre() != null && e.getNombre().equalsIgnoreCase(nuevosDatos.getNombre())) ||
                    (nuevosDatos.getSalario() != null && e.getSalario().equals(nuevosDatos.getSalario()))
                )
                .toList();

        empleados.forEach(e -> {
            if (nuevosDatos.getNombre() != null) {
                e.setNombre(nuevosDatos.getNombre());
            }
            if (nuevosDatos.getSalario() != null) {
                e.setSalario(nuevosDatos.getSalario());
            }
        });

        return empleadoRepository.saveAll(empleados);
    }
    @GetMapping("/sort")
    public List<Empleado> ordenarPorSalario(@RequestParam String salario) {
        return "desc".equalsIgnoreCase(salario) 
            ? empleadoRepository.findAll().stream().sorted((a, b) -> b.getSalario().compareTo(a.getSalario())).toList()
            : empleadoRepository.findAll().stream().sorted((a, b) -> a.getSalario().compareTo(b.getSalario())).toList();
    }
    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado nuevosDatos) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);
        
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleado.setNombre(nuevosDatos.getNombre());
            empleado.setSalario(nuevosDatos.getSalario());
            return empleadoRepository.save(empleado);
        }
        
        return null;
    }
}