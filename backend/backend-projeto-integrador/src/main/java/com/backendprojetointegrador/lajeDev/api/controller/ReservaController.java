package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.ReservaAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ReservaInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ReservaOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import com.backendprojetointegrador.lajeDev.domain.model.Reserva;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import com.backendprojetointegrador.lajeDev.domain.service.ProdutoService;
import com.backendprojetointegrador.lajeDev.domain.service.ReservaService;
import com.backendprojetointegrador.lajeDev.domain.service.UsuarioService;
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
@RequestMapping("/reservas")
@Tag(name = "Reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaAssembler reservaAssembler;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    @Operation(description = "cadastra uma reserva",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = ReservaOutput.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping
    public ResponseEntity<ReservaOutput> criar(@RequestBody @Valid ReservaInput reserva) {
        Reserva reservaToSave = reservaAssembler.toEntity(reserva);

        Usuario usuario = usuarioService.buscarUsuario(reserva.getUsuario()).get();
        reservaToSave.setUsuario(usuario);

        Produto produto = produtoService.buscarProduto(reserva.getProduto());
        reservaToSave.setProduto(produto);

        ReservaOutput reservaOutput = reservaAssembler.toOutput(reservaService.criarReserva(reservaToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(reservaOutput);
    }

    @Operation(description = "lista todos reservas")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ReservaOutput.class))))
    @GetMapping
    public List<ReservaOutput> listar() {
        return reservaAssembler.toCollectionOutput(reservaService.listarReservas());
    }

    @Operation(description = "lista reservas por produto")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ReservaOutput.class))))
    @GetMapping("/listarPorProduto/{idProduto}")
    public ResponseEntity<List<ReservaOutput>> listarPorProduto(@PathVariable Long idProduto) {
        Produto produto = produtoService.buscarProduto(idProduto);
        List<Reserva> reservasEntity = reservaService.listarReservasPorProduto(produto);
        List<ReservaOutput> reservasOutputs = reservaAssembler.toCollectionOutput(reservasEntity);
        return ResponseEntity.ok(reservasOutputs);
    }

    @Operation(description = "lista reservas por usuário")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ReservaOutput.class))))
    @GetMapping("/listarPorCliente/{idUsuario}")
    public ResponseEntity<List<ReservaOutput>> listarPorCliente(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.buscarUsuario(idUsuario).get();
        List<Reserva> reservasEntity = reservaService.listarReservasPorUsuario(usuario);
        List<ReservaOutput> reservaOutputs = reservaAssembler.toCollectionOutput(reservasEntity);
        return ResponseEntity.ok(reservaOutputs);
    }
}
