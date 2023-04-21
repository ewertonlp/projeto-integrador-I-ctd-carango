package com.backendprojetointegrador.lajeDev.api.dtos.inputs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservaInput {

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaDeInicioDaReserva;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicialDaReserva;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinalDaReserva;
    @NotNull
    private Long usuario;
    @NotNull
    private Long produto;
}
