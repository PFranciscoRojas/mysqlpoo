package com.example.testucc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Método para GET /Productos/search?nombre=xx
    List<Empleado> findByNombreContaining(String nombre);
}