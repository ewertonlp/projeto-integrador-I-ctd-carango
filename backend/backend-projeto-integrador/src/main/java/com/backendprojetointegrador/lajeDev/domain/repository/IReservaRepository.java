package com.backendprojetointegrador.lajeDev.domain.repository;

import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import com.backendprojetointegrador.lajeDev.domain.model.Reserva;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByProduto(Produto produto);
    List<Reserva> findByUsuario(Usuario usuario);
}
