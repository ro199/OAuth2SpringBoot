package com.krugger.resourceserver.controller;

import com.krugger.resourceserver.dto.MessageDto;
import com.krugger.resourceserver.entity.Vacuna;
import com.krugger.resourceserver.service.VacunaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacunas")
@RequiredArgsConstructor
public class VacunaController {

    private final VacunaService vacunaService;

    @PostMapping("/{id}/vacuna")
    public MessageDto registrarVacuna(@PathVariable Long id, @RequestBody Vacuna vacuna) {
        return vacunaService.registrarVacuna(id, vacuna);
    }
}
