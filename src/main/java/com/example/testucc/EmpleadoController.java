package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoRepository.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void updateEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        empleadoService.updateEmpleado(id, empleado);
    }

    @GetMapping("/search")
    public List<Empleado> encontrarEmpleado(@RequestParam String nombre) {
        return empleadoService.findNombre(nombre);
    }

    @PatchMapping("/patch/{id}")
    public void updateParcialmenteEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        empleadoService.updateEmpleadoSalario(id, empleado.getSalario());
    }

    @PutMapping("/batch")
    public List<Empleado> updateListaEmpleados(@RequestBody List<Empleado> empleados) {
        empleadoService.updateSalariosEmpleados(empleados);
        return empleados;
    }

    @GetMapping("/sort")
    public List<Empleado> obtenerSalariosEmpleadoOrdenados(Double salario) {
        return empleadoService.findSalariosOrdenado(salario);
    }
}