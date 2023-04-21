package com.backendprojetointegrador.lajeDev.domain.service.imagem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImagemResult {
    private Long imagemId;
    private String uploadSignedUrl;
}