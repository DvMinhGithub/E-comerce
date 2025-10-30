package com.api.ecomerce.services.impl;

import org.springframework.stereotype.Service;

import com.api.ecomerce.exceptions.ExceptionFactory;
import com.api.ecomerce.models.Role;
import com.api.ecomerce.models.RoleType;
import com.api.ecomerce.repositories.RoleRepository;
import com.api.ecomerce.services.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(RoleType roleType) {
        return roleRepository
                .findByName(roleType.getName())
                .orElseThrow(() -> ExceptionFactory.resourceNotFound("Role", "name", roleType.getName()));
    }

    @Override
    public Role getOrCreateRole(RoleType roleType) {
        return roleRepository.findByName(roleType.getName()).orElseGet(() -> {
            Role role = roleType.toRole();
            return roleRepository.save(role);
        });
    }

    @Override
    public Role createRole(RoleType roleType) {
        Role role = roleType.toRole();
        return roleRepository.save(role);
    }
}
