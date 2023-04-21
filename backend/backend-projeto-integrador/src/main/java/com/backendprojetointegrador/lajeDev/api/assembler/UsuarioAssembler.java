package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.UsuarioInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.UsuarioOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsuarioAssembler {

    private final ModelMapper modelMapper;

    public UsuarioOutput toOutput(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioOutput.class);
    }

    public List<UsuarioOutput> toCollectionOutput(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toOutput).collect(Collectors.toList());
    }
    public Usuario toEntity(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

}
