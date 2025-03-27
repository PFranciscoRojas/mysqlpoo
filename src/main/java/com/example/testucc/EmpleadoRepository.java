package com.example.testucc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByNombreContaining(String nombre);
    List<Empleado> findAllByOrderBySalarioDesc();
    List<Empleado> findAllByOrderBySalarioAsc();
}