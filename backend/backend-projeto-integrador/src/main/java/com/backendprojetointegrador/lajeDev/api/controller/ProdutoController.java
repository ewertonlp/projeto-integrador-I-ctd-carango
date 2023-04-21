package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.ImagemAssembler;
import com.backendprojetointegrador.lajeDev.api.assembler.ProdutoAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ProdutoInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ProdutoOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.*;
import com.backendprojetointegrador.lajeDev.domain.service.CaracteristicaService;
import com.backendprojetointegrador.lajeDev.domain.service.CategoriaService;
import com.backendprojetointegrador.lajeDev.domain.service.CidadeService;
import com.backendprojetointegrador.lajeDev.domain.service.ProdutoService;
import com.backendprojetointegrador.lajeDev.domain.service.imagem.ImagemService;
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

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoAssembler produtoAssembler;
    private final CaracteristicaService caracteristicaService;
    private final ImagemService imagemService;
    private final ImagemAssembler imagemAssembler;
    private final CategoriaService categoriaService;
    private final CidadeService cidadeService;

    @Operation(description = "cadastra uma produto",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = ProdutoOutput.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping
    public ResponseEntity<ProdutoOutput> criar(@RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoToSave = produtoAssembler.toEntity(produtoInput);

        List<Caracteristica> caracteristicas = caracteristicaService
                .listarDeterminandasCaracteristicas(produtoInput.getCaracteristicas());
        produtoToSave.setCaracteristicas(caracteristicas);

        if(produtoInput.getImagens() != null){
            List<Imagem> imagens = imagemService.listaDeterminadasImagens(produtoInput.getImagens());
            produtoToSave.setImagens(imagens);
        }

        Categoria categoria = categoriaService.buscarCategoria(produtoInput.getCategoria());
        produtoToSave.setCategoria(categoria);

        Cidade cidade = cidadeService.buscarCidadeById(produtoInput.getCidade());
        produtoToSave.setCidade(cidade);

        ProdutoOutput produtoOutput = produtoAssembler.toOutput(produtoService.criarProduto(produtoToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoOutput);
    }

    @Operation(description = "atualiza uma produto",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = ProdutoOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PutMapping("{idProduto}")
    public ResponseEntity<?> atualizar(@PathVariable Long idProduto,
                                       @RequestBody @Valid ProdutoInput produtoInput) {
            Produto produtoToSave = produtoAssembler.toEntity(produtoInput);

            List<Caracteristica> caracteristicas = caracteristicaService
                    .listarDeterminandasCaracteristicas(produtoInput.getCaracteristicas());
            produtoToSave.setCaracteristicas(caracteristicas);

            List<Imagem> imagens = imagemService.listaDeterminadasImagens(produtoInput.getImagens());
            produtoToSave.setImagens(imagens);

            Categoria categoria = categoriaService.buscarCategoria(produtoInput.getCategoria());
            produtoToSave.setCategoria(categoria);

            Cidade cidade = cidadeService.buscarCidadeById(produtoInput.getCidade());
            produtoToSave.setCidade(cidade);

            ProdutoOutput produtoOutput = produtoAssembler.toOutput(produtoService.atualizaProduto(idProduto,
                    produtoToSave));

            return ResponseEntity.status(HttpStatus.OK).body(produtoOutput);
    }

    @Operation(description = "lista todos produtos")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ProdutoOutput.class))))
    @GetMapping
    public List<ProdutoOutput> listar() {
        List<Produto> produtosEntity = produtoService.listarProdutos();
        List<ProdutoOutput> produtoOutputs = produtoAssembler.toCollectionOutput(produtosEntity);
        return produtoOutputs;
    }

    @Operation(description = "busca um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = ProdutoOutput.class))),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoOutput> buscarById(@PathVariable Long idProduto) {
        Produto produtoEntity = produtoService.buscarProduto(idProduto);
        ProdutoOutput produtoOutput = produtoAssembler.toOutput(produtoEntity);
        return ResponseEntity.ok(produtoOutput);
    }

    @Operation(description = "lista produtos por categoria")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ProdutoOutput.class))))
    @GetMapping("/listarPorCategoria")
    public ResponseEntity<List<ProdutoOutput>> listarPorCategoria(@RequestParam("categoria") Long idCategoria) {
        Categoria categoria = categoriaService.buscarCategoria(idCategoria);
        List<Produto> produtosEntitys = produtoService.listarByCategoria(categoria);
        List<ProdutoOutput> produtoOutputs = produtoAssembler.toCollectionOutput(produtosEntitys);
        return ResponseEntity.ok(produtoOutputs);
    }

    @Operation(description = "lista produtos por cidade")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ProdutoOutput.class))))
    @GetMapping("/listarPorCidade")
    public List<ProdutoOutput> listarPorCidade(@RequestParam("cidade") Long idCidade) {
        Cidade cidade = cidadeService.buscarCidadeById(idCidade);
        List<Produto> produtosEntitys = produtoService.listarByCidade(cidade);
        List<ProdutoOutput> produtoOutputs = produtoAssembler.toCollectionOutput(produtosEntitys);
        return produtoOutputs;
    }

    @Operation(description = "lista produtos por cidades e intervalo de datas")
    @ApiResponse(responseCode = "200", content =
    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =
            ProdutoOutput.class))))
    @GetMapping("/listarPorCidadeEDatas/{idCidade}")
    public ResponseEntity<List<ProdutoOutput>> listarPorCidadeAndDatas(@PathVariable Long idCidade,
                        @RequestParam("dateStart") LocalDate dateStart, @RequestParam("dateEnd") LocalDate dateEnd) {
        List<Produto> produtosReservados = produtoService.listarByCidadeAndDates(idCidade, dateStart, dateEnd);
        List<ProdutoOutput> produtoOutputs = produtoAssembler.toCollectionOutput(produtosReservados);
        return ResponseEntity.ok(produtoOutputs);
    }

    @Operation(description = "exclui um produto",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content =
            @Content),
            @ApiResponse(responseCode = "404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> deletar(@PathVariable Long idProduto) {
        produtoService.excluirProduto(idProduto);
        return ResponseEntity.noContent().build();
    }
}
