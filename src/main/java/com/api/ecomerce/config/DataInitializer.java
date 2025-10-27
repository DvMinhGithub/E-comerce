package com.api.ecomerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.ecomerce.models.Role;
import com.api.ecomerce.models.RoleType;
import com.api.ecomerce.models.User;
import com.api.ecomerce.repositories.RoleRepository;
import com.api.ecomerce.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initAdminUser();
    }

    private void initAdminUser() {
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            System.out.println("Admin user already exists");
            return;
        }

        Role adminRole = roleRepository
                .findByName(RoleType.ADMIN.getName())
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        User adminUser = User.builder()
                .firstName(adminFirstName)
                .lastName(adminLastName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(adminRole)
                .isAdmin(true)
                .build();

        userRepository.save(adminUser);
        System.out.println("Admin user created successfully!");
        System.out.println("Email: admin@ecommerce.com");
        System.out.println("Password: admin123");
    }
}
