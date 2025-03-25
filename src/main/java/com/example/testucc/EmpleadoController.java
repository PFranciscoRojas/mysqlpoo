package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PatchMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado nuevosDatos) {
        Empleado empleado = empleadoRepository.findById(id).get();

        if (nuevosDatos.getNombre() != null) {
            empleado.setNombre(nuevosDatos.getNombre());
        }

        return empleadoRepository.save(empleado);
    }

    @PutMapping("/batch")
    public List<Empleado> actualizarEmpleados(@RequestBody List<Empleado> empleados) {
        return empleadoRepository.saveAll(empleados);
    }

    @GetMapping("/sort")
    public List<Empleado> obtenerEmpleadosOrdenados(@RequestParam String salario, @RequestParam String orden) {
        Sort.Direction direccion = orden.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return empleadoRepository.findAll(Sort.by(direccion, salario));

    }

    @GetMapping
    public List<Empleado> obtenerTodo() {
        return empleadoRepository.findAll();
    }

    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Empleado con ID " + id + " no encontrado");
        }
    }
}
