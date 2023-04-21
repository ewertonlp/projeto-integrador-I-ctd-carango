package com.backendprojetointegrador.lajeDev.domain.service.imagem;

import com.backendprojetointegrador.lajeDev.domain.model.Imagem;

import java.net.URL;

public interface CloudStorageProvider {
    URL generatePresignerUploadUrl(Imagem imagem);
}
