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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @GetMapping("/buscar")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre){
        return empleadoRepository.findByNombre(nombre);
    }

    @PatchMapping("/{id}")
    public Empleado actualizarParcial(@PathVariable Long id, @RequestBody Empleado empleadoParcial) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        if (empleadoParcial.getNombre() != null) {
            empleadoExistente.setNombre(empleadoParcial.getNombre());
        }
        if (empleadoParcial.getSalario() != 0) {
            empleadoExistente.setSalario(empleadoParcial.getSalario());
        }
        return empleadoRepository.save(empleadoExistente);
    }

    @PutMapping("/batch")
    public List<Empleado> actulizarBatch(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    @GetMapping("/ordenar")
    public List<Empleado> ordenarPorSalario(@RequestParam(name = "orden", defaultValue = "asc") String orden) {
        if (orden.equals("asc")) {
            return empleadoRepository.findAllByOrderBySalarioAsc();
        } else {
            return empleadoRepository.findAllByOrderBySalarioDesc();
        }
    }

    @PutMapping("/{id}")
    public Empleado actulizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setSalario(empleado.getSalario());
        return empleadoRepository.save(empleadoExistente);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }


}


