package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.Role;
import com.mima.mimafhprojektbackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public Role findByName(String user) {
        return roleRepository.findByName(user).orElseThrow();
    }
}
