package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaracteristicaInput {

    @NotBlank
    @Size(min =1, max = 60)
    private String nome;
    @NotBlank
    private String icone;
}
