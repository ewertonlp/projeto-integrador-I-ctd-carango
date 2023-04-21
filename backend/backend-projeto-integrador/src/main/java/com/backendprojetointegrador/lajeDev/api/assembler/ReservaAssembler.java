package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ReservaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ReservaOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Reserva;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ReservaAssembler {

    private final ModelMapper modelMapper;

    public ReservaOutput toOutput(Reserva reserva) {
        return modelMapper.map(reserva, ReservaOutput.class);
    }

    public List<ReservaOutput> toCollectionOutput(List<Reserva> reservas) {
        return reservas.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Reserva toEntity(ReservaInput reservaInput) {
        return modelMapper.map(reservaInput, Reserva.class);
    }
}
