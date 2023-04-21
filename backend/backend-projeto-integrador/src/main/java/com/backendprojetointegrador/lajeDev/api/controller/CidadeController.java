package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.CidadeAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CidadeInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CidadeOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.service.CidadeService;
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
@RequestMapping("/cidades")
@Tag(name = "Cidades")
public class CidadeController {

    private final CidadeService cidadeService;
    private final CidadeAssembler cidadeAssembler;


    @Operation(description = "cadastra uma cidade",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CidadeOutput.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping
    public ResponseEntity<CidadeOutput> criar(@RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeToSave = cidadeAssembler.toEntity(cidadeInput);
        CidadeOutput cidadeOutput = cidadeAssembler.toOutput(cidadeService.criarCidade(cidadeToSave));
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeOutput);
    }

    @Operation(description = "atualiza uma cidade",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CidadeOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PutMapping("/{idCidade}")
    public ResponseEntity<CidadeOutput> atualizar(@PathVariable Long idCidade,
                                                  @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeEntity = cidadeAssembler.toEntity(cidadeInput);
        CidadeOutput cidadeOutput = cidadeAssembler.toOutput(cidadeService.atualizaCidade(idCidade, cidadeEntity));
        return ResponseEntity.ok(cidadeOutput);
    }

    @Operation(description = "lista todas cidades")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            CidadeOutput.class))))
    @GetMapping
    public List<CidadeOutput> listar() {
       List<Cidade> cidadesEntitys = cidadeService.listarCidade();
       List<CidadeOutput> cidadesOutputs = cidadeAssembler.toCollectionOutput(cidadesEntitys);
        return cidadesOutputs;
    }

    @Operation(description = "busca uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CidadeOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @GetMapping("/{idCidade}")
    public ResponseEntity<CidadeOutput> buscarById(@PathVariable Long idCidade) {
        Cidade cidadeEntity = cidadeService.buscarCidadeById(idCidade);
        CidadeOutput cidadeOutput = cidadeAssembler.toOutput(cidadeEntity);
        return ResponseEntity.ok(cidadeOutput);
    }

    @Operation(description = "exclui uma cidade",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content =
            @Content),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @DeleteMapping("/{idCidade}")
    public ResponseEntity<Void> deletar(@PathVariable Long idCidade) {
        cidadeService.excluirCidadeById(idCidade);
        return ResponseEntity.noContent().build();
    }
}
