package com.krugger.resourceserver.service;

import com.krugger.resourceserver.dto.EmpleadoDTO;
import com.krugger.resourceserver.dto.MessageDto;
import com.krugger.resourceserver.entity.Empleado;
import com.krugger.resourceserver.entity.Role;
import com.krugger.resourceserver.entity.User;
import com.krugger.resourceserver.enums.RoleName;
import com.krugger.resourceserver.repository.EmpleadoRepository;
import com.krugger.resourceserver.repository.RoleRepository;
import com.krugger.resourceserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Empleado obtenerEmpleadoPorCedula(String cedula) {
        return empleadoRepository.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrada"));
    }

    public MessageDto actualizarEmpleado(@Validated @RequestBody EmpleadoDTO empleadoDTO) {
        return empleadoRepository.findByCedula(empleadoDTO.getCedula())
                .map(empleado -> {
                    empleado.setNombres(empleadoDTO.getNombres());
                    empleado.setApellidos(empleadoDTO.getApellidos());
                    empleado.setCorreo(empleadoDTO.getCorreo());
                    empleadoRepository.save(empleado);
                    return new MessageDto("Empleado "+ empleado.getCedula() + " Actualizado");
                })
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    public MessageDto eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
        return new MessageDto("Empleado Eliminado");
    }
}
