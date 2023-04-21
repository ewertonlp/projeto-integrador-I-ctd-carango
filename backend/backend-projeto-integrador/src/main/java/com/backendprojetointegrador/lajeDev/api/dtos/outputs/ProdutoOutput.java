package com.backendprojetointegrador.lajeDev.api.dtos.outputs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoOutput {

    private Long id;
    private String nome;
    private String descricao;
    private String vin;
    private List<CaracteristicaOutput> caracteristicas;
    private List<ImagemOutput> imagens;
    private CategoriaOutput categoria;
    private CidadeOutput cidade;
}
