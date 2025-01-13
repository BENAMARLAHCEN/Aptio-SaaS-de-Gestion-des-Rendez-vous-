package com.youcode.aptio.util;

import com.youcode.aptio.model.Role;
import com.youcode.aptio.model.User;
import com.youcode.aptio.repository.RoleRepository;
import com.youcode.aptio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run (String...args){
        List<String> roles = List.of(
                "ROLE_USER",           // Basic user role (already exists)
                "ROLE_ADMIN",          // Admin role (already exists)
                "ROLE_BUSINESS_OWNER", // For business owners/managers
                "ROLE_EMPLOYEE"       // For business employees
        );

        roles.forEach(roleName -> roleRepository
                .findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName, LocalDateTime.now(), LocalDateTime.now())))
        );
    }
}