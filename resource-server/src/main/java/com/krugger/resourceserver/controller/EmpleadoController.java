package com.krugger.resourceserver.controller;

import com.krugger.resourceserver.dto.EmpleadoDTO;
import com.krugger.resourceserver.dto.MessageDto;
import com.krugger.resourceserver.entity.Empleado;
import com.krugger.resourceserver.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MessageDto registrarEmpleado(@Validated @RequestBody EmpleadoDTO empleado) {
        return empleadoService.registrarEmpleado(empleado);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Empleado> listarEmpleados() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/buscarEmpleados")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Empleado> buscarEmpleadosConFiltros(
            @RequestParam(name = "vacunado", required = false) Boolean vacunado,
            @RequestParam(name = "tipoVacuna", required = false) String tipoVacuna,
            @RequestParam(name = "fechaInicio", required = false) LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false) LocalDate fechaFin
    ) {
        return empleadoService.buscarEmpleadosConFiltros(vacunado, tipoVacuna, fechaInicio, fechaFin);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public MessageDto actualizarEmpleado(@Validated @RequestBody EmpleadoDTO empleadoDTO) {
        return empleadoService.actualizarEmpleado(empleadoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public MessageDto eliminarEmpleado(@PathVariable Long id) {
        return empleadoService.eliminarEmpleado(id);
    }
}
