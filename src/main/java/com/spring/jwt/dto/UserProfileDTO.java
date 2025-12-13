package com.spring.jwt.dto;


import com.spring.jwt.profile.UserProfileDTO2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    // Base user information
    private UserDTO user;

    // Role-specific information
//    private UserProfileDTO2 userProfileDTO1;


    // User roles
    private Set<String> roles;
}
