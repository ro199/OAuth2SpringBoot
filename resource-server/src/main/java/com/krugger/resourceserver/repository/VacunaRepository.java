package com.krugger.resourceserver.repository;

import com.krugger.resourceserver.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacunaRepository extends JpaRepository<Vacuna, Integer> {
}
