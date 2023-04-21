package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ProdutoInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ProdutoOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProdutoAssembler  {

    private final ModelMapper modelMapper;

    public ProdutoOutput toOutput(Produto produto) {
        return modelMapper.map(produto, ProdutoOutput.class);
    }

    public List<ProdutoOutput> toCollectionOutput(List<Produto> produtos) {
        return produtos.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Produto toEntity(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
}
