package com.api.ecomerce.models;

public enum RoleType {
    USER("USER", "Regular user with basic permissions"),
    ADMIN("ADMIN", "Administrator with full access");

    private final String name;
    private final String description;

    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Role toRole() {
        return Role.builder().name(this.name).description(this.description).build();
    }
}
