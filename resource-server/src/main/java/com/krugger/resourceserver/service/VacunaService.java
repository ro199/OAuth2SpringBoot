package com.krugger.resourceserver.service;

import com.krugger.resourceserver.dto.MessageDto;
import com.krugger.resourceserver.entity.Empleado;
import com.krugger.resourceserver.entity.Vacuna;
import com.krugger.resourceserver.repository.EmpleadoRepository;
import com.krugger.resourceserver.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacunaService {
    private final VacunaRepository vacunaRepository;
    private final EmpleadoRepository empleadoRepository;

    public MessageDto registrarVacuna(Long id, Vacuna vacuna) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrada"));
        vacuna.setEmpleado(empleado);
        empleado.getVacunas().add(vacuna);
        empleadoRepository.save(empleado);
        return new MessageDto("Vacuna agregada correctamente a el empleado");
    }
}
