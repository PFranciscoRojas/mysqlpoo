package com.example.testucc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    public List<Empleado> findByNombre(String nombre);
    public List<Empleado> findAllByOrderBySalarioDesc();

} 
