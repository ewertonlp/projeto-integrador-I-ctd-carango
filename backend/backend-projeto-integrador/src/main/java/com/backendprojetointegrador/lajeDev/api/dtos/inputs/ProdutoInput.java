package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    @Size(min =1, max = 60)
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    @Size(min =1, max = 17)
    private String vin;
    @NotEmpty
    private List<Long> caracteristicas;
    @Valid
    private List<Long> imagens;
    @NotNull
    private Long categoria;
    @NotNull
    private Long cidade;
}
