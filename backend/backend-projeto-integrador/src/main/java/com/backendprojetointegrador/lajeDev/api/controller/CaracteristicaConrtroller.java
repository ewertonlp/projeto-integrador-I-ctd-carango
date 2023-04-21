package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.CaracteristicaAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CaracteristicaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CaracteristicaOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import com.backendprojetointegrador.lajeDev.domain.service.CaracteristicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/caracteristicas")
@Tag(name = "Caracterísitcas")
public class CaracteristicaConrtroller {

    private final CaracteristicaService caracteristicaService;
    private final CaracteristicaAssembler caracteristicaAssembler;

    @Operation(description = "cadastra uma característica",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CaracteristicaOutput.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping
    public ResponseEntity<CaracteristicaOutput> criar(@RequestBody @Valid CaracteristicaInput caracteristicaInput) {
        Caracteristica caracteristicaToSave = caracteristicaAssembler.toEntity(caracteristicaInput);
        CaracteristicaOutput caracteristicaOutput = caracteristicaAssembler
                .toOutput(caracteristicaService.criarCaracteristica(caracteristicaToSave));
        return ResponseEntity.status(HttpStatus.CREATED).body(caracteristicaOutput);
    }

    @Operation(description = "atualiza uma característica",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CaracteristicaOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PutMapping("/{idCaracteristica}")
    public ResponseEntity<CaracteristicaOutput> atualizar(@PathVariable Long idCaracteristica,
                                                          @RequestBody @Valid CaracteristicaInput caracteristicaInput) {
        Caracteristica caracteristicaEntity = caracteristicaAssembler.toEntity(caracteristicaInput);
        CaracteristicaOutput caracteristicaOutput = caracteristicaAssembler
                .toOutput(caracteristicaService.atualizaCaracteristica(idCaracteristica, caracteristicaEntity));
        return ResponseEntity.ok(caracteristicaOutput);
    }

    @Operation(description = "lista todas característica")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            CaracteristicaOutput.class))))
    @GetMapping
    public List<CaracteristicaOutput> listar() {
        List<Caracteristica> caracteristicasEntity = caracteristicaService.listarCaracteristicas();
        List<CaracteristicaOutput> caracteristicaOutputs = caracteristicaAssembler
                .toCollectionOutput(caracteristicasEntity);
        return caracteristicaOutputs;
    }

    @Operation(description = "busca uma característica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CaracteristicaOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @GetMapping("/{idCaracteristica}")
    public ResponseEntity<CaracteristicaOutput> buscarById(@PathVariable Long idCaracteristica) {
        Caracteristica caracteristicaEntity = caracteristicaService.buscarCaracteristica(idCaracteristica);
        CaracteristicaOutput caracteristicaOutput = caracteristicaAssembler.toOutput(caracteristicaEntity);
        return ResponseEntity.ok(caracteristicaOutput);
    }

    @Operation(description = "exclui uma característica",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content =
            @Content),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @DeleteMapping("/{idCaracteristica}")
    public ResponseEntity<Void> deletar(@PathVariable Long idCaracteristica) {
        caracteristicaService.excluirCaracteristica(idCaracteristica);
        return ResponseEntity.noContent().build();
    }
}

