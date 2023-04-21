package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.repository.ICidadeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CidadeService {

    private final ICidadeRepository cidadeRepository;

    public Cidade criarCidade(Cidade cidade){
        boolean cidadeExiste = cidadeRepository.findByNome(cidade.getNome()).stream()
                .anyMatch((cidadeExistente) -> !cidadeExistente.equals(cidade));

        if (cidadeExiste) {
            log.info("Criação de Cidade falhou - Cidade de nome: " + cidade.getNome() + ". Já existe!");
            throw new RecursoJaExistenteException("Cidade com de nome: " + cidade.getNome() +
                    " já existe. Tente novamente!");
        }
        return cidadeRepository.save(cidade);
    }
    public Cidade atualizaCidade(Long idCidade, Cidade cidade) {
        if (!existeCidadeById(idCidade)) {
            log.info("Atualização de Cidade falhou - Cidade de id: " + idCidade + ". Não existe!");
            throw new RecursoNaoEncontrado("Cidade de id: "+ idCidade + ". Não existe!");
        }
        cidade.setId(idCidade);
        return criarCidade(cidade);
    }

    public Cidade buscarCidadeById(Long idCidade) {
        return cidadeRepository.findById(idCidade)
                .orElseThrow(() -> {
                        log.info("Busca por uma Cidade falhou - Cidade de id: " + idCidade + ". Não existe!");
                    return new RecursoNaoEncontrado("Cidade de id: "+ idCidade + " não existe!");
                });
    }

    public List<Cidade> listarCidade() {
        return cidadeRepository.findAll();
    }


    public void excluirCidadeById(Long idCidade) {
        if (!existeCidadeById(idCidade)) {
            log.info("Exclusão de Cidade falhou - Cidade de id: " +    idCidade + ". Não existe!");
            throw new RecursoNaoEncontrado("Cidade de id: "+ idCidade + " não existe!");
        }
        cidadeRepository.deleteById(idCidade);
    }

    public boolean existeCidadeById(Long idCidade) {
        return cidadeRepository.existsById(idCidade);
    }

}
