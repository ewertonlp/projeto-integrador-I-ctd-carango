package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.CategoriaAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CategoriaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.CategoriaOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.service.CategoriaService;
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
@RequestMapping("/categorias")
@Tag(name = "Categorias")
public class CategoriaController {

    CategoriaService categoriaService;
    CategoriaAssembler categoriaAssembler;

    @Operation(description = "cadastra uma categoria",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CategoriaOutput.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping
    public ResponseEntity<CategoriaOutput> criar(@RequestBody @Valid CategoriaInput categoria) {
        Categoria categoriaToSave = categoriaAssembler.toEntity(categoria);
        CategoriaOutput categoriaOutput = categoriaAssembler.toOutput(categoriaService.criarCategoria(categoriaToSave));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaOutput);
    }

    @Operation(description = "atualiza uma característica",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CategoriaOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaOutput> atualizar(@PathVariable Long idCategoria,
                                                     @RequestBody @Valid CategoriaInput categoriaInput) {
        Categoria categoriaToUpdate = categoriaAssembler.toEntity(categoriaInput);
        CategoriaOutput categoriaOutput = categoriaAssembler
                .toOutput(categoriaService.atualizarCategoria(idCategoria, categoriaToUpdate));
        return ResponseEntity.ok(categoriaOutput);
    }

    @Operation(description = "lista todas categorias")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            CategoriaOutput.class))))
    @GetMapping
    public List<CategoriaOutput> listar() {
        List<Categoria> categoriasEntity = categoriaService.listarCategorias();
        List<CategoriaOutput> categoriaOutputs = categoriaAssembler.toCollectionOutput(categoriasEntity);
        return categoriaOutputs;
    }

    @Operation(description = "busca uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = CategoriaOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaOutput> buscarById(@PathVariable Long idCategoria) {
        Categoria categoriaEntity = categoriaService.buscarCategoria(idCategoria);
        CategoriaOutput categoriaOutput = categoriaAssembler.toOutput(categoriaEntity);
        return ResponseEntity.ok(categoriaOutput);
    }

    @Operation(description = "exclui uma categoria",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content =
            @Content),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> deletar(@PathVariable Long idCategoria) {
        categoriaService.excluirCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }

}
