package com.krugerChallenge.authorizationserver;

import com.krugerChallenge.authorizationserver.entity.Role;
import com.krugerChallenge.authorizationserver.enums.RoleName;
import com.krugerChallenge.authorizationserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerApplication implements CommandLineRunner {
	/*@Autowired
	RoleRepository roleRepository;*/

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Role admindRole = Role.builder().role(RoleName.ROLE_ADMIN).build();
		Role userRole = Role.builder().role(RoleName.ROLE_USER).build();
		roleRepository.save(admindRole);
		roleRepository.save(userRole);*/

	}
}
