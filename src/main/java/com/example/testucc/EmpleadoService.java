package com.example.testucc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> getEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> findByNombre(String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    public Empleado updateEmpleadoNombre(Long id, Empleado empleado) {
        return empleadoRepository.findById(id).map(existingEmpleado -> {
            existingEmpleado.setSalario(empleado.getSalario());
            return empleadoRepository.save(existingEmpleado);
        }).orElseThrow(() -> new RuntimeException("Empleado not found with id " + id));
    }

    public List<Empleado> getEmpleadosSortedBySalarioDesc() {
        return empleadoRepository.findAllByOrderBySalarioDesc();
    }

    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado updateEmpleado(Long id, Empleado empleado) {
        return empleadoRepository.findById(id).map(e -> {
            e.setNombre(empleado.getNombre());
            e.setSalario(empleado.getSalario());
            return empleadoRepository.save(e);
        }).orElseThrow(() -> new RuntimeException("Empleado not found with id " + id));
    }

    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    public void updateAllEmpleados(List<Empleado> empleadosActualizados) {
        List<Long> ids = empleadosActualizados.stream()
                .map(Empleado::getId)
                .toList();
    
        List<Empleado> empleados = empleadoRepository.findAllById(ids);
    
        empleados.forEach(empleado -> 
            empleadosActualizados.stream()
                .filter(e -> e.getId().equals(empleado.getId()))
                .findFirst()
                .ifPresent(e -> empleado.setSalario(e.getSalario()))
        );
    
        empleadoRepository.saveAll(empleados);
    }
    
}
