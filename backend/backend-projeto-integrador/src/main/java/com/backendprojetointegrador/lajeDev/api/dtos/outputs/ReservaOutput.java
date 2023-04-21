package com.backendprojetointegrador.lajeDev.api.dtos.outputs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservaOutput {

    private Long id;

    @JsonFormat(pattern = "hh:mm:ss a")
    private LocalTime horaDeInicioDaReserva;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicialDaReserva;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinalDaReserva;
    private UsuarioOutput Usuario;
    private ProdutoOutput produto;
}
