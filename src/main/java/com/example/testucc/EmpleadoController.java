package com.example.testucc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping
    public List<Empleado> obtenerTodo(){
        return empleadoRepository.findAll();
    }

    @PostMapping
    public Empleado guardarTodo(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepository.delete(empleado);
    }

    @PutMapping("/{id}")
    public Empleado update(@PathVariable Long id, @RequestBody Empleado empleado){
        Empleado empleadoToUpdate = empleadoRepository.findById(id).get();
        empleadoToUpdate.setNombre(empleado.getNombre());
        empleadoToUpdate.setSalario(empleado.getSalario());
        empleadoRepository.save(empleadoToUpdate);
        return empleadoToUpdate;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Empleado empleado = empleadoRepository.findById(id).get();
        empleadoRepository.delete(empleado);
    }

    @GetMapping("/searchA")
    public Empleado getByNameA(@RequestParam String nombre){
        Empleado busqueda = new Empleado();
        for (Empleado e : empleadoRepository.findAll()){
            if (e.getNombre().equals(nombre)){
                busqueda = e;
            }
        }
        return busqueda;
    }

    @GetMapping("/search")
    public List<Empleado> getByName(@RequestParam String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @PatchMapping("/{id}")
    public Empleado patchEmpleado(@PathVariable Long id, @RequestBody Empleado empleado){
        Empleado empleadoToUpdate = empleadoRepository.findById(id).get();
        if(empleado.getNombre() != null){
            empleadoToUpdate.setNombre(empleado.getNombre());
        }
        if(empleado.getSalario() != 0){
            empleadoToUpdate.setSalario(empleado.getSalario());
        }
        empleadoRepository.save(empleadoToUpdate);
        return empleadoToUpdate;
    }

    @PutMapping("/batch")
    public List<Empleado> updateMultiple(@RequestBody List<Empleado> empleados){
        List<Empleado> updated = new ArrayList<Empleado>();
        for(Empleado e : empleados){
            Empleado empleadoToUpdate = empleadoRepository.findById(e.getId()).get();
            empleadoToUpdate.setSalario(e.getSalario());
            empleadoRepository.save(empleadoToUpdate);
            updated.add(empleadoToUpdate);
        }
        return updated;
    }

    @GetMapping("/sort")
    public List<Empleado> getOrdered(@RequestParam(defaultValue = "desc") String order) {
        Sort.Direction direction = Sort.Direction.DESC;
        return empleadoRepository.findAll(Sort.by(direction, "salario"));
    }

}
