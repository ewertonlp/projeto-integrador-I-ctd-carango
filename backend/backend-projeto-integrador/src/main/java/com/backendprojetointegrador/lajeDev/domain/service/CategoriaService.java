package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.repository.ICategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoriaService {

    private final ICategoriaRepository categoriasRepository;

    public Categoria criarCategoria(Categoria categoria) {
        boolean categoriaExiste = categoriasRepository.findByQualificacao(categoria.getQualificacao()).stream()
                .anyMatch(categoriaExistente -> !categoriaExistente.equals(categoria));

        if(categoriaExiste) {
            log.info("Criação de Categoria falhou - Categoria de qualificação: "
                    + categoria.getQualificacao() + " . Já existe!");
            throw new RecursoJaExistenteException("Categoria com a qualificação "
                    + categoria.getQualificacao() + " já existe. Tente novamente!");
        }
        return categoriasRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long idCategoria, Categoria categoria) {
        if (!existeCategoria(idCategoria)) {
            log.info("Atualização de Categoria falhou - Categoria de id: " + idCategoria +
                    ". Não existe!");
            throw new RecursoNaoEncontrado("Categoria com de id: " + idCategoria
                    + " não existe!");

        }
        categoria.setId(idCategoria);
        return criarCategoria(categoria);
    }

    public Categoria buscarCategoria(Long idCategoria) {
        return categoriasRepository.findById(idCategoria)
                .orElseThrow(() -> {
                    log.info("Busca por uma Categoria falhou - Categoria de id: " + idCategoria +
                            ". Não existe!");
                    return new RecursoNaoEncontrado("Categoria com de id: " + idCategoria
                            + " não existe!");
                });
    }

    public List<Categoria> listarCategorias() {
        return categoriasRepository.findAll();
    }

    public void excluirCategoria(Long idCategoria) {
        if (!existeCategoria(idCategoria)) {
            log.info("Search of Categoria falied- Categoria de id: " + idCategoria +
                    ". Não existe!");
            throw new RecursoNaoEncontrado("Categoria com de id: " + idCategoria
                    + " não existe!");
        }
        categoriasRepository.deleteById(idCategoria);
    }

    public boolean existeCategoria(Long idCategoria) {
        return categoriasRepository.existsById(idCategoria);
    }
}
