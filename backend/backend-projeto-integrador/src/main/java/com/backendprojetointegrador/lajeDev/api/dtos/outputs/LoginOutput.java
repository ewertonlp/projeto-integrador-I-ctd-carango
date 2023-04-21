package com.backendprojetointegrador.lajeDev.api.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoginOutput {

    private Long id;
    private String nomeESobrenome;
    private String email;
    private String token;
    private List<RoleOutput> roles;
}
