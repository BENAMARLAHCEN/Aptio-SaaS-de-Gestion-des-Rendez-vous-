package com.youcode.aptio.util;

import com.youcode.aptio.model.Role;
import com.youcode.aptio.model.User;
import com.youcode.aptio.repository.RoleRepository;
import com.youcode.aptio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        roles.forEach(roleName -> roleRepository
                .findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName, LocalDateTime.now())))
        );

//        List<String> emails = List.of("admin@admin.com");
//        emails.forEach(email -> userRepository
//                .findByEmail(email)
//                .orElse(userRepository.save(User.builder()
//                        .email(email)
//                        .username(email)
//                        .password(passwordEncoder.encode("admin"))
//                        .firstName("Admin")
//                        .lastName("Admin")
//                        .role(roleRepository.findByName("ROLE_ADMIN").orElseThrow())
//                        .createdAt(LocalDateTime.now())
//                        .isActive(true)
//                        .build()
//                    )
//                )
//        );
    }
}