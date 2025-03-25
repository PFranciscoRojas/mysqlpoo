package com.example.testucc;

import java.util.List;

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
    private EmpleadoService empleadoService;


    // http://localhost:8080/empleados
    @GetMapping
    public List<Empleado> getEmpleados() {
        return empleadoService.getEmpleados();
    }

    //// http://localhost:8080/empleados/search?nombre=river
    @GetMapping("/search")
    public List<Empleado> findByNombre(@RequestParam String nombre) {
        return empleadoService.findByNombre(nombre);
    }

    // http://localhost:8080/empleados/1
    @PatchMapping("/{id}")
    public Empleado updateEmpleadoNombre(@PathVariable Long id, @RequestBody Empleado empleado) {
        return empleadoService.updateEmpleadoNombre(id, empleado);
    }

    // http://localhost:8080/empleados/sort
    @GetMapping("/sort")
    public List<Empleado> getEmpleadosSortedBySalarioDesc() {
        return empleadoService.getEmpleadosSortedBySalarioDesc();
    }

    // http://localhost:8080/empleados
    @PostMapping
    public Empleado saveEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.saveEmpleado(empleado);
    }

    //// http://localhost:8080/empleados/1
    @PutMapping("/{id}")
    public Empleado updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        return empleadoService.updateEmpleado(id, empleado);
    }

    // http://localhost:8080/empleados/1
    @DeleteMapping("/{id}")
    public void deleteEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
    }


    // http://localhost:8080/empleados/update-all
    @PutMapping("/update-all")
    public void updateAllEmpleados(@RequestBody List<Empleado> empleadosActualizados) {
        empleadoService.updateAllEmpleados(empleadosActualizados);
    }
    
}
