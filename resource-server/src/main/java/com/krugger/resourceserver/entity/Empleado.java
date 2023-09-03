package com.krugger.resourceserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cedula;

    private String nombres;
    private String apellidos;
    private String correo;
    private Date fechaNacimiento;
    private String direccionDomicilio;
    private String telefonoMovil;
    private boolean vacunado;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private Set<Vacuna> vacunas;

}
