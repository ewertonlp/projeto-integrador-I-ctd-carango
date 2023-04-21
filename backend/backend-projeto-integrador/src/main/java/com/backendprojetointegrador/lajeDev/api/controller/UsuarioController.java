package com.backendprojetointegrador.lajeDev.api.controller;

import com.backendprojetointegrador.lajeDev.api.assembler.UsuarioAssembler;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.RolesInput;
import com.backendprojetointegrador.lajeDev.api.dtos.inputs.UsuarioInput;
import com.backendprojetointegrador.lajeDev.api.dtos.outputs.UsuarioOutput;
import com.backendprojetointegrador.lajeDev.api.exception_handler.Problema;
import com.backendprojetointegrador.lajeDev.domain.model.Role;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import com.backendprojetointegrador.lajeDev.domain.repository.IRoleRepository;
import com.backendprojetointegrador.lajeDev.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "cadastro de novos usuários e gerenciamento de usuários já existentes")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final IRoleRepository roleRepository;
    private final UsuarioAssembler usuarioAssembler;

    @Operation(description = "endpoint para cadastros de novos usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema =
            @Schema(implementation = Problema.class)))})
    @PostMapping
    public ResponseEntity<String> cria(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioEntity = usuarioAssembler.toEntity(usuarioInput);

        Boolean criouUsuario = usuarioService.criarUsuario(usuarioEntity);

        return criouUsuario ?
                ResponseEntity.badRequest().body("Usuário com e-mail " + usuarioInput.getEmail() + " já existe.") :
                ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(description = "endpoint para adição de funções a usuários dentro da aplicação",
            security = 
                    @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content),
            @ApiResponse(responseCode = "404", description = "Usuário com id: \" idUsuarioPassado \" não existe!",
                    content = @Content)}  )
    @PatchMapping("{idUsuario}")
    public ResponseEntity<String> adicionarRolesUsuario(@Parameter(required = true) @PathVariable Long idUsuario,
                                                          @RequestBody @Valid RolesInput rolesInput) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuario(idUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioEntity = usuarioOptional.get();

            usuarioEntity.setId(idUsuario);

            rolesInput.getRoles().forEach(idRole -> {
                usuarioEntity.getRoles().add(roleRepository.findById(idRole).get());
            });

            usuarioService.criarUsuario(usuarioEntity);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com id: " + idUsuario + " não existe!");
    }

    @Operation(description = "endpoint para adição de funções a usuários dentro da aplicação",
            security =
                    @SecurityRequirement(name = "Bearer token"))
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation =
            UsuarioOutput.class)), mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<List<UsuarioOutput>> listar() {
        List<UsuarioOutput> usuarioOutputs = usuarioAssembler
                .toCollectionOutput(usuarioService.listarTodosUsuarios());
        return ResponseEntity.ok(usuarioOutputs);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listarRoles() {
        return ResponseEntity.ok(usuarioService.listarRoles());
    }

    @Operation(description = "endpoint para adição de funções a usuários dentro da aplicação",
            security =
                    @SecurityRequirement(name = "Bearer token"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário com id: \" idUsuarioPassado \" não existe!",
                    content = @Content)}  )
    @DeleteMapping("{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable Long idUsuario) {
        usuarioService.excluirUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
