package com.backendprojetointegrador.lajeDev.api.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioOutput {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private List<RoleOutput> roles;
}
