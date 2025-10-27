package com.api.ecomerce.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String avatarUrl;
    private String roleName;
    private boolean isAdmin;
    private boolean authenticated;
}
