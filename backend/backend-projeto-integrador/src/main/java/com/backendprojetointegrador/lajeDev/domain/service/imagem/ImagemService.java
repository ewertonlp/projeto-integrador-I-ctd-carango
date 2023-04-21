package com.backendprojetointegrador.lajeDev.domain.service.imagem;

import com.backendprojetointegrador.lajeDev.api.assembler.ImagemAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ImagemInput;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Imagem;
import com.backendprojetointegrador.lajeDev.domain.repository.IImagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImagemService {

    private final CloudStorageProvider cloudStorageProvider;
    private final IImagemRepository imagemRepository;

    public UploadImagemResult generateUploadUrl(Imagem imagem) {
        boolean imagemExiste = imagemRepository.findByName(imagem.getName())
                .stream().anyMatch(imagemExistente -> !imagemExistente.equals(imagem));

        if (imagemExiste) {
            throw new RecursoJaExistenteException("Imagem com nome: " + imagem.getName() + " já existe!");
        }

        URL presignerUploadUrl = cloudStorageProvider.generatePresignerUploadUrl(imagem);

        Pattern pattern = Pattern.compile("(^h.*\\?)");
        Matcher urlImagem = pattern.matcher(presignerUploadUrl.toString());
        if(urlImagem.find()) {
            imagem.setUrl(urlImagem.group().replace("?", ""));
        }

        imagemRepository.save(imagem);

        return new UploadImagemResult(imagem.getId(), presignerUploadUrl.toString());
    }

    public List<Imagem> listaDeterminadasImagens(List<Long> idImagens) {
        return idImagens.stream().map(idImagem -> {
            Optional<Imagem> optionalImagem = imagemRepository.findById(idImagem);
            if (optionalImagem.isPresent()) {
                return optionalImagem.get();
            }
            throw new RecursoNaoEncontrado("Não existe imagem de id: " + idImagem);
        }).collect(Collectors.toList());
    }

    public List<Imagem> criaObjetosImagens(List<ImagemInput> imagensInput, ImagemAssembler imagemAssembler) {
        List<Imagem> imagens = imagensInput.stream().map(imagemInput -> {
            Imagem imagemEntity = imagemAssembler.toEntity(imagemInput);
            return imagemEntity;
        }).collect(Collectors.toList());

        return imagens;
    }

}
