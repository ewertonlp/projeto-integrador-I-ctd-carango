package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @Email(regexp = "[A-Za-z0-9_.-]+@([A-Za-z0-9_]+.)+[A-Za-z]{2,4}")
    private String email;
    @NotBlank
    private String senha;
}
