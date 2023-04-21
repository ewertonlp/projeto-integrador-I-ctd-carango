package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ImagemInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ImagemOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Imagem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ImagemAssembler {

    private final ModelMapper modelMapper;

    public ImagemOutput toOutput(Imagem imagem) {
        return modelMapper.map(imagem, ImagemOutput.class);
    }

    public List<ImagemOutput> toCollectionOutput(List<Imagem> imagens) {
        return imagens.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Imagem toEntity(ImagemInput imagemInput) {
        return modelMapper.map(imagemInput, Imagem.class);
    }
}
