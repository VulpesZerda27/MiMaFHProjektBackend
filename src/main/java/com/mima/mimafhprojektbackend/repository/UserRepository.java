package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
}
