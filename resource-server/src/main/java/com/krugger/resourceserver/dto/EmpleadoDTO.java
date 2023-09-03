package com.krugger.resourceserver.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class EmpleadoDTO {
    @NotBlank(message = "El campo Cédula es requerido.")
    @Pattern(regexp = "\\d{10}", message = "La cédula debe tener 10 dígitos numéricos")
    private String cedula;
    @NotBlank(message = "El campo Nombres es requerido.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Nombres inválidos.")
    private String nombres;
    @NotBlank(message = "El campo Apellidos es requerido.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Apellidos inválidos.")
    private String apellidos;
    @NotBlank(message = "El campo Correo Electrónico es requerido.")
    @Email(message = "Correo Electrónico inválido.")
    private String correo;
    private Date fechaNacimiento;
    private String direccionDomicilio;
    private String telefonoMovil;
    private boolean vacunado;
}
