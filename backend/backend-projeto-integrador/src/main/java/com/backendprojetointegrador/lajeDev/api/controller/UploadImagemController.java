package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.ImagemAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ImagemInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.LoginOutput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.ReservaOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.service.imagem.ImagemService;
import com.backendprojetointegrador.lajeDev.domain.service.imagem.UploadImagemResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/upload")
@Tag(name = "Upload Imagem")
public class UploadImagemController {

    private final ImagemService imagemService;
    private final ImagemAssembler imagemAssembler;

    @Operation(description = "cria uma url pré assinada para realizar upload de imagens para o bucket s3",
            security =
            @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation
                    = UploadImagemResult.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Problema.class)) )}  )
    @PostMapping("/imagens")
    public UploadImagemResult uploadImagens(@RequestBody @Valid ImagemInput imagem) {
        return this.imagemService.generateUploadUrl(imagem.toDomain());
    }
}
