package com.example.testucc;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public void updateEmpleado(long id, Empleado empleado) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);

        if (empleadoExistente.isPresent()) {
            Empleado miEmpleado = empleadoExistente.get();
            miEmpleado.setNombre(empleado.getNombre());
            miEmpleado.setSalario(empleado.getSalario());
            empleadoRepository.save(miEmpleado);
        } else {
            throw new RuntimeException("Empleado no encontrado");
        }
    }

    public List<Empleado> findNombre(String nombre){
        return empleadoRepository.findByNombre(nombre);
    }

    public void updateEmpleadoSalario(long id, double salario) {
        Optional<Empleado> empleadoExistente = empleadoRepository.findById(id);

        if (empleadoExistente.isPresent()) {
            Empleado miEmpleado = empleadoExistente.get();
            miEmpleado.setSalario(salario);
            empleadoRepository.save(miEmpleado);
        } else  {
            throw new RuntimeException("Empleado no encontrado");
        }
    }

    @Transactional
    public void updateSalariosEmpleados(List<Empleado> empleados) {
        List<Empleado> empleadosActualizar = new ArrayList<>();
        for (Empleado empleado : empleados) {
            Optional<Empleado> miEmpleado = empleadoRepository.findById(empleado.getId());
            if (miEmpleado.isPresent()) {
                miEmpleado.get().setSalario(empleado.getSalario());
                empleadosActualizar.add(miEmpleado.get());
            } else {
                throw new RuntimeException("Empleado no encontrado");
            }
        }
        empleadoRepository.saveAll(empleadosActualizar);
    }

   public List<Empleado> findSalariosOrdenado(Double salario) {
        return empleadoRepository.findAllByOrderBySalarioDesc();
    }
}
