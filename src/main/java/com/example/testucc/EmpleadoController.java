package com.example.testucc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;

    // GET /Productos/search?nombre=xx
    @SuppressWarnings("unchecked")
    @GetMapping("/search")
    public List<Empleado> buscarPorNombre(@RequestParam String nombre) {
        return (List<Empleado>) empleadoRepository.findByNombreContaining(nombre);
    }

    // GET /Productos/sort?precio,desc
    @GetMapping("/sort")
    public List<Empleado> obtenerOrdenados(@RequestParam String salario, @RequestParam String desc) {
        if (desc.equalsIgnoreCase("true")) {
            return empleadoRepository.findAll(org.springframework.data.domain.Sort.by(salario).descending());
        }
        return empleadoRepository.findAll(org.springframework.data.domain.Sort.by(salario).ascending());
    }

    // PUT /Productos/batch/
    @PostMapping("/batch")
    public List<Empleado> guardarLote(@RequestBody List<Empleado> empleados) {
        List<Empleado> empleadosActualizados = new ArrayList<>();
        for (Empleado empleado : empleados) {
            Empleado empleadoExistente = empleadoRepository.findById(empleado.getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
            empleadoExistente.setSalario(empleado.getSalario());
            empleadosActualizados.add(empleadoRepository.save(empleadoExistente));
        }
        return empleadosActualizados;
    }

    // PUT /Productos/{id}
    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    // DELETE /Productos/{id}
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
    }
}
