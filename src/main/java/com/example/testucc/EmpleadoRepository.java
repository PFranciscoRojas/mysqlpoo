package com.example.testucc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  EmpleadoRepository extends JpaRepository<empleado, Long>{

       List<empleado> findByNombre(String nombre);

       List<empleado> findBySalarioGreaterThanEqual(double salario); 

       List<empleado> findByOrderBySalarioDesc();
}
