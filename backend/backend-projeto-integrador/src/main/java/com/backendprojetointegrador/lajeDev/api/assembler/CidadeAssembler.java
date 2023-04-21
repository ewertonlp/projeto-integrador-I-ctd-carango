package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CidadeInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CidadeOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CidadeAssembler {

    private final ModelMapper modelMapper;

    public CidadeOutput toOutput(Cidade cidade) {
        return modelMapper.map(cidade, CidadeOutput.class);
    }

    public List<CidadeOutput> toCollectionOutput(List<Cidade> cidades) {
        return cidades.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Cidade toEntity(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }
}
