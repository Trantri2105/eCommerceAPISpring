package com.trantring.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "Email is required!")
    @Email(message = "Email is invalid!")
    private String email;

    @NotNull(message = "Password is required!")
    private String password;
}
