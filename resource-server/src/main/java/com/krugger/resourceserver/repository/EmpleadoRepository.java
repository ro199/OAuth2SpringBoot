package com.krugger.resourceserver.repository;

import com.krugger.resourceserver.entity.Empleado;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByCedula(String cedula);
    Optional<Empleado> findById(Long id);

    List<Empleado> findAll(Specification specification);

    Optional<Empleado> deleteById(Long id);

}
