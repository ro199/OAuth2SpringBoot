package com.krugger.resourceserver.repository;

import com.krugger.resourceserver.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByCedula(String cedula);
    Optional<Empleado> findById(Long id);

    Optional<Empleado> deleteById(Long id);

}
