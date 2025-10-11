package com.api.ecomerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.ecomerce.models.Role;
import com.api.ecomerce.models.RoleType;
import com.api.ecomerce.models.User;
import com.api.ecomerce.repositories.RoleRepository;
import com.api.ecomerce.repositories.UserRepository;
import com.api.ecomerce.services.RoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initAdminUser();
    }

    private void initRoles() {
        for (RoleType roleType : RoleType.values()) {
            if (!roleRepository.existsByName(roleType.getName())) {
                roleService.createRole(roleType);
                System.out.println("Created " + roleType.getName() + " role");
            }
        }

        System.out.println("Role initialization completed!");
    }

    private void initAdminUser() {
        if (userRepository.findByEmail("admin@ecommerce.com").isPresent()) {
            System.out.println("Admin user already exists");
            return;
        }

        Role adminRole = roleRepository.findByName(RoleType.ADMIN.getName())
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        User adminUser = User.builder()
                .firstName("Admin")
                .lastName("User")
                .email("admin@ecommerce.com")
                .password(passwordEncoder.encode("admin123"))
                .role(adminRole)
                .isAdmin(true)
                .build();

        userRepository.save(adminUser);
        System.out.println("Admin user created successfully!");
        System.out.println("Email: admin@ecommerce.com");
        System.out.println("Password: admin123");
    }
}
