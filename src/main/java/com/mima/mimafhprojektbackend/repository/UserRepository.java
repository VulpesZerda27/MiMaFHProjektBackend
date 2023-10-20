package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.MyUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByEmail(@Email String userEmail);
}
