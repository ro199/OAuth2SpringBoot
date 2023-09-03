package com.krugerChallenge.authorizationserver.repository;

import com.krugerChallenge.authorizationserver.entity.Role;
import com.krugerChallenge.authorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
