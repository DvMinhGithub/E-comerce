package com.api.ecomerce.services;

import com.api.ecomerce.models.Role;
import com.api.ecomerce.models.RoleType;

public interface RoleService {

    Role getRoleByName(RoleType roleType);

    Role getOrCreateRole(RoleType roleType);

    Role createRole(RoleType roleType);
}
