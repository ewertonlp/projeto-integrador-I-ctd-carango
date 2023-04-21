package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CategoriaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CategoriaOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoriaAssembler {

    private final ModelMapper modelMapper;

    public CategoriaOutput toOutput(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaOutput.class);
    }

    public List<CategoriaOutput> toCollectionOutput(List<Categoria> categorias) {
        return categorias.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Categoria toEntity(CategoriaInput categoriaInput) {
        return modelMapper.map(categoriaInput, Categoria.class);
    }
}
