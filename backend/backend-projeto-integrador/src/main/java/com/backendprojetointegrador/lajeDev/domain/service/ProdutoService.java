package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import com.backendprojetointegrador.lajeDev.domain.repository.IProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProdutoService {

    private final IProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        boolean produtoExiste = produtoRepository.findByVin(produto.getVin()).stream()
                .anyMatch((produtoExistente) -> !produtoExistente.equals(produto));

        if (produtoExiste){
            log.info("Criação de Produto falhou - Produto com o VIN: "+ produto.getVin() + ". Já existe!");
            throw new RecursoJaExistenteException("Produto com VIN: " + produto.getVin() +
                    " já existe. Tente novamente!");
        }
        return produtoRepository.save(produto);
    }

    public Produto atualizaProduto(Long idProduto, Produto produto) {
        if (!existeProduto(idProduto)) {
            log.info("Atualização de Produto falhou - Produto com o id: "+ idProduto + ". Não existe!");
            throw new RecursoNaoEncontrado("Produto com o id: " + idProduto + ". Não existe!");
        }
        produto.setId(idProduto);
        return criarProduto(produto);
    }

    public Produto buscarProduto(Long idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> {
                    log.info("Busca por um Produto falhou - Produto com o id: "+ idProduto + ". Não existe!");
                    return new RecursoNaoEncontrado("Produto com o id: "+ idProduto +
                            ". Não existe!");
                });
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarByCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> listarByCidade(Cidade cidade) {
        return produtoRepository.findByCidade(cidade);
    }

    public List<Produto> listarByCidadeAndDates(Long idCidade, LocalDate dateStar, LocalDate dateEnd) {
        return produtoRepository.findByProdutosNotReservados(idCidade, dateStar, dateEnd);
    }

    public void excluirProduto(Long idProduto) {
        if (!existeProduto(idProduto)) {
            log.info("Exclusão de Produto falhou - Produto com o id: "+ idProduto + ". Não existe!");
            throw new RecursoNaoEncontrado("Produto com o id: "+ idProduto +". Não existe!");
        }
        produtoRepository.deleteById(idProduto);
    }

    public boolean existeProduto(Long idProduto) {
        return produtoRepository.existsById(idProduto);
    }

}
