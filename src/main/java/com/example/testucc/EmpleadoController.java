package com.example.testucc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/search")
    public List<Empleado> getEmpleadosSearch(@RequestParam String nombre) {
        return empleadoRepository.findByNombreContaining(nombre);
    }

    @GetMapping("/sort")
    public List<Empleado> getEmpleadosSort(@RequestParam String salarios) {
        return "descendiente".equalsIgnoreCase(salarios) 
            ? empleadoRepository.findAllByOrderBySalarioDesc() 
            : empleadoRepository.findAllByOrderBySalarioAsc();
    }

    @PatchMapping("/{id}/")
    public Empleado patchEmpleadosId(@PathVariable Long id, @RequestBody Empleado updates) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if(updates.getNombre() != null) empleado.setNombre(updates.getNombre());
        if(updates.getSalario() > 0) empleado.setSalario(updates.getSalario());
        
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/batch/")
    public List<Empleado> putEmpleadosBatch(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    @PutMapping("/{id}")
    public Empleado putEmpleadosId(@PathVariable Long id, @RequestBody Empleado empleado) {
        if(!empleadoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmpleadosId(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }
}