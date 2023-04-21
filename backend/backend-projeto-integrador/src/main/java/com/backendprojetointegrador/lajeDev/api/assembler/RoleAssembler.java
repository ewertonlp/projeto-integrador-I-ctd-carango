package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.outputs.RoleOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleAssembler {

    private final ModelMapper modelMapper;

    public RoleOutput toOutput(Role role) {
        return modelMapper.map(role, RoleOutput.class);
    }

    public List<RoleOutput> toCollectionOutput(List<Role> roles) {
        return roles.stream().map(this::toOutput).collect(Collectors.toList());
    }
}
