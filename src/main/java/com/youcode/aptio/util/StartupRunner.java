package com.youcode.aptio.util;

import com.youcode.aptio.model.Role;
import com.youcode.aptio.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public StartupRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        roles.forEach(roleName -> roleRepository
                .findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName, LocalDateTime.now())))
        );
    }
}
