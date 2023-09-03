package com.krugger.resourceserver.repository;

import com.krugger.resourceserver.entity.Role;
import com.krugger.resourceserver.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleName rolename);

}
