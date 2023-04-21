package com.backendprojetointegrador.lajeDev.api.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaOutput {

    private Long id;
    private String qualificacao;
    private String descricao;
    private String urlImagem;
}
