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
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }

    // Buscar empleados por nombre
    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombreContaining(nombre);
    }

    // Actualizar parcialmente un empleado
    @PatchMapping("/{id}")
    public Empleado actualizarParcial(@PathVariable Long id, @RequestBody Empleado empleadoParcial) {
        return empleadoRepository.findById(id).map(empleado -> {
            if (empleadoParcial.getNombre() != null) {
                empleado.setNombre(empleadoParcial.getNombre());
            }
            if (empleadoParcial.getSalario() != 0) {
                empleado.setSalario(empleadoParcial.getSalario());
            }
            return empleadoRepository.save(empleado);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    // Actualizar varios empleados a la vez
    @PutMapping("/butch")
    public List<Empleado> actualizarVarios(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    // Obtener empleados ordenados por salario descendente
    @GetMapping("/sort")
    public List<Empleado> obtenerOrdenados(@RequestParam(defaultValue = "desc") String salario) {
        return salario.equalsIgnoreCase("desc") ? 
            empleadoRepository.findAllByOrderBySalarioDesc() : 
            empleadoRepository.findAllByOrderBySalarioAsc();
    }

    // Actualizar completamente un empleado
    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado nuevoEmpleado) {
        return empleadoRepository.findById(id).map(empleado -> {
            empleado.setNombre(nuevoEmpleado.getNombre());
            empleado.setSalario(nuevoEmpleado.getSalario());
            return empleadoRepository.save(empleado);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }
}
