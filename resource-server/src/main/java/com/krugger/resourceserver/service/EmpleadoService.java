package com.krugger.resourceserver.service;

import com.krugger.resourceserver.dto.EmpleadoDTO;
import com.krugger.resourceserver.dto.MessageDto;
import com.krugger.resourceserver.entity.Empleado;
import com.krugger.resourceserver.entity.Role;
import com.krugger.resourceserver.entity.User;
import com.krugger.resourceserver.entity.Vacuna;
import com.krugger.resourceserver.enums.RoleName;
import com.krugger.resourceserver.enums.TipoVacuna;
import com.krugger.resourceserver.repository.EmpleadoRepository;
import com.krugger.resourceserver.repository.RoleRepository;
import com.krugger.resourceserver.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public MessageDto registrarEmpleado(EmpleadoDTO empleadoDTO) {
        Empleado empleado = Empleado.builder()
                .cedula(empleadoDTO.getCedula())
                .nombres(empleadoDTO.getNombres())
                .apellidos(empleadoDTO.getApellidos())
                .correo(empleadoDTO.getCorreo())
                .fechaNacimiento(empleadoDTO.getFechaNacimiento())
                .direccionDomicilio(empleadoDTO.getDireccionDomicilio())
                .telefonoMovil(empleadoDTO.getTelefonoMovil())
                .vacunado(empleadoDTO.isVacunado())
                .build();

        User user = User.builder()
                .username("C-"+empleado.getCedula())
                .password(passwordEncoder.encode(empleado.getCedula()))
                .build();
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRole(RoleName.valueOf("ROLE_USER"))
                .orElseThrow(() -> new RuntimeException("Rol no econtrado"));
        roles.add(role);
        user.setRoles(roles);
        user.setEmpleado(empleado);

        empleadoRepository.save(empleado);
        userRepository.save(user);
        return new MessageDto("Empleado "+ empleado.getCedula() + " Registrado con Usuario:" + user.getUsername() + " y clave: " +empleado.getCedula());
    }

    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> buscarEmpleadosConFiltros(Boolean vacunado, String tipoVacuna, LocalDate fechaInicio, LocalDate fechaFin) {
        Specification<Empleado> specification = Specification.where(null);

        if (vacunado != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                if (vacunado) {
                    return criteriaBuilder.isTrue(root.get("vacunado"));
                } else {
                    return criteriaBuilder.isFalse(root.get("vacunado"));
                }
            });
        }

        if (tipoVacuna != null && !tipoVacuna.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                Join<Empleado, Vacuna> join = root.join("vacunas", JoinType.LEFT);
                return criteriaBuilder.equal(join.get("tipoVacuna"), tipoVacuna);
            });
        }

        if (fechaInicio != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaNacimiento"), Date.from(fechaInicio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            });
        }

        if (fechaFin != null) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                return criteriaBuilder.lessThanOrEqualTo(root.get("fechaNacimiento"), Date.from(fechaFin.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            });
        }

        // Aplica la especificación para buscar empleados
        return empleadoRepository.findAll(specification);
    }

    public MessageDto actualizarEmpleado(@Validated @RequestBody EmpleadoDTO empleadoDTO) {
        return empleadoRepository.findByCedula(empleadoDTO.getCedula())
                .map(empleado -> {
                    empleado.setNombres(empleadoDTO.getNombres());
                    empleado.setApellidos(empleadoDTO.getApellidos());
                    empleado.setCorreo(empleadoDTO.getCorreo());
                    if(empleado.isVacunado()){
                        if(empleadoDTO.getVacunas() == null || empleadoDTO.getVacunas().isEmpty()){
                            throw new RuntimeException("Informacion de Vacuna es requerida");
                        }

                        Set<Vacuna> vacunas = new HashSet<>();
                        empleadoDTO.getVacunas().forEach(vacuna -> {
                            try {
                                TipoVacuna tipoVacuna = TipoVacuna.valueOf(vacuna.getTipoVacuna());
                            } catch (IllegalArgumentException e) {
                                throw new RuntimeException("Tipo de vacuna requerido");
                            }

                            if(vacuna.getFechaVacunacion() == null){
                                throw new RuntimeException("Fecha de Vacunacion requerida");
                            }

                            if(vacuna.getNumeroDosis() == -1){
                                throw new RuntimeException("Numero de dosis requerido");
                            }

                            vacunas.add(vacuna);

                        });
                        empleado.setVacunas(vacunas);

                    }
                    empleadoRepository.save(empleado);
                    return new MessageDto("Empleado "+ empleado.getCedula() + " Actualizado");
                })
                .orElseThrow(() -> new RuntimeException("Empleado no encontrada"));
    }

    public MessageDto eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
        return new MessageDto("Empleado Eliminado");
    }
}
