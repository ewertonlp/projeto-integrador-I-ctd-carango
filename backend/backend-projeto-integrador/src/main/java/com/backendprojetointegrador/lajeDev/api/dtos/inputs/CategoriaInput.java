package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaInput {

    @NotBlank
    @Size(min =1, max = 60)
    private String qualificacao;
    @NotBlank
    private String descricao;
    @NotBlank
    private String urlImagem;
}
