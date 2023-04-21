package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import com.backendprojetointegrador.lajeDev.domain.model.Imagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagemInput {

    @NotBlank
    @Size(min = 1, max = 60)
    private String name;

    @NotBlank
    private String contentType;

    @NotNull
    private Long contentLength;

    public Imagem toDomain() {
        return Imagem.builder()
                .name(this.name)
                .contentType(this.contentType)
                .contentLength(this.contentLength)
                .build();
    }
}
