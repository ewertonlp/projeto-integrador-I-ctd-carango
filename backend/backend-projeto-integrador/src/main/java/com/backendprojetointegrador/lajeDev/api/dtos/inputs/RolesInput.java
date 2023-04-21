package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesInput {

    @NotEmpty
    private List<Long> roles;
}
