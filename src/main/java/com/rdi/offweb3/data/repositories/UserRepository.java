package com.rdi.offweb3.data.repositories;

import com.rdi.offweb3.enums.Role;
import com.rdi.offweb3.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String username);

    User findUserByRole(Role role);
}
