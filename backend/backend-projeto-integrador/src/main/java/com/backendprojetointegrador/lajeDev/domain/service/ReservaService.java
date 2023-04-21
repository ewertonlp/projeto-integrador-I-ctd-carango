package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import com.backendprojetointegrador.lajeDev.domain.model.Reserva;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import com.backendprojetointegrador.lajeDev.domain.repository.IReservaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReservaService {

    private final IReservaRepository reservaRepository;

    public Reserva criarReserva(Reserva reserva) {
        Boolean reservaExiste = reservaRepository.findByProduto(reserva.getProduto())
                .stream().anyMatch(reserva1 -> reserva1.equals(reserva));

        if(reservaExiste) {
            log.info("Criação de Reserva falhou - Produto: " + reserva.getProduto() + " já está reservado no periódo " +
                    "de datas solicitado!");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            throw new RecursoJaExistenteException("Produto: " + reserva.getProduto().getNome().toUpperCase()
                    + " já reservado no período: Data inicial " +
                    reserva.getDataInicialDaReserva().format(formatter) +  " - Data final " +
                    reserva.getDataFinalDaReserva().format(formatter) + ". Tente novamente em outro período!");
        }

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservasPorProduto(Produto produto) {
        return reservaRepository.findByProduto(produto);
    }

    public List<Reserva> listarReservasPorUsuario(Usuario usuario) {
        return reservaRepository.findByUsuario(usuario);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }
}
