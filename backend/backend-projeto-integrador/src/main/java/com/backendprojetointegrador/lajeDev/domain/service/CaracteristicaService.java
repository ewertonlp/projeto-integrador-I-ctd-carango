package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.domain.exception.RecursoJaExistenteException;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import com.backendprojetointegrador.lajeDev.domain.repository.ICaracteristicasRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CaracteristicaService {

    private final ICaracteristicasRepository caracteristicasRepository;

    public Caracteristica criarCaracteristica(Caracteristica caracteristica) {
        boolean caracteristicaExiste = caracteristicasRepository.findByNome(caracteristica.getNome()).stream()
                .anyMatch(caracteristicaExistente -> !caracteristicaExistente.equals(caracteristica));

        if (caracteristicaExiste) {
            log.info("Criação de Característica falhou - Característica de nome: " + caracteristica.getNome()
                    + " . Já existe!");
            throw new RecursoJaExistenteException("Característica com o nome " + caracteristica.getNome() +
                    " já existe. Tente novamente!");
        }

        return caracteristicasRepository.save(caracteristica);
    }

    public Caracteristica atualizaCaracteristica(Long idCaracteristica, Caracteristica caracteristica) {
        if (!existeCaracteristica(idCaracteristica)) {
             log.info("Atualização de Característica falhou - Característica de id: " + idCaracteristica
                            + ". Não existe!");
             throw new RecursoNaoEncontrado("Caracteristica de id: " + idCaracteristica +
                    " não existe!");
        }
        caracteristica.setId(idCaracteristica);
        return criarCaracteristica(caracteristica);
    }

    public List<Caracteristica> listarCaracteristicas() {
        return caracteristicasRepository.findAll();
    }

    public List<Caracteristica> listarDeterminandasCaracteristicas(List<Long> idCaracteristicas){
        List<Caracteristica> caracteristicas = idCaracteristicas.stream()
                .map((idCaracteristica) -> {
                    Caracteristica caracteristica = new Caracteristica();
                    BeanUtils.copyProperties(caracteristicasRepository.findById(idCaracteristica).get(), caracteristica);
                    return caracteristica;
                }).collect(Collectors.toList());
        return caracteristicas;
    }

    public Caracteristica buscarCaracteristica(Long idCaracteristica) {
        return caracteristicasRepository.findById(idCaracteristica).orElseThrow(() -> {
                log.info("Busca por uma Característica falhou- Característica de id: " + idCaracteristica
                                + ". Não existe!");
                 return  new RecursoNaoEncontrado("Caracteristica de id: " + idCaracteristica +
                        " não existe!");
            });
    }

    public void excluirCaracteristica(Long idCaracteristica) {
        if (!existeCaracteristica(idCaracteristica)) {
            log.info("Exclusão de Característica falhou- Característica de id: " + idCaracteristica
                            + ". Não existe!");
            throw new RecursoNaoEncontrado("Caracteristica de id: " + idCaracteristica +
                    " não existe!");
        }
        caracteristicasRepository.deleteById(idCaracteristica);
    }

    public boolean existeCaracteristica(Long idCaracteristica) {
        return caracteristicasRepository.existsById(idCaracteristica);
    }

}