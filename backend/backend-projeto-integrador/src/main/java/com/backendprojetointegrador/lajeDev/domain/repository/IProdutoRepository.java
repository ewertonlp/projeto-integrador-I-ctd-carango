package com.backendprojetointegrador.lajeDev.domain.repository;

import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByVin(String vin);
    List<Produto> findByCidade(Cidade cidade);
    List<Produto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Produto p WHERE p.cidade.id = :idCidade " +
            "AND p.id NOT IN " +
            "(SELECT r.produto.id FROM Reserva r " +
            "WHERE((r.dataInicialDaReserva BETWEEN :dateStart AND  :dateEnd) " +
            "OR (r.dataFinalDaReserva BETWEEN :dateStart AND :dateEnd)))")
    List<Produto> findByProdutosNotReservados(Long idCidade, LocalDate dateStart,  LocalDate dateEnd);
}
