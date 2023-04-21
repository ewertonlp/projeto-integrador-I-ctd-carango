package com.backendprojetointegrador.lajeDev.api.assembler;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CaracteristicaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CaracteristicaOutput;
import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CaracteristicaAssembler {

    private final ModelMapper modelMapper;

    public CaracteristicaOutput toOutput(Caracteristica caracteristica) {
        return modelMapper.map(caracteristica, CaracteristicaOutput.class);
    }

    public List<CaracteristicaOutput> toCollectionOutput(List<Caracteristica> caracteristicas) {
        return caracteristicas.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Caracteristica toEntity(CaracteristicaInput caracteristicaInput) {
        return modelMapper.map(caracteristicaInput, Caracteristica.class);
    }
 }
