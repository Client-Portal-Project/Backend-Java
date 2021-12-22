package com.projectx.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
