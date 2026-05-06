package br.com.kaique.techchallenge.controllers;

import br.com.kaique.techchallenge.dtos.LoginRequestDTO;
import br.com.kaique.techchallenge.dtos.SaveUsuarioRequestDTO;
import br.com.kaique.techchallenge.dtos.UpdateSenhaUsuarioRequestDTO;
import br.com.kaique.techchallenge.dtos.UpdateUsuarioRequestDTO;
import br.com.kaique.techchallenge.entities.Usuario;
import br.com.kaique.techchallenge.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.kaique.techchallenge.config.SwaggerExamples.ATUALIZACAO_SENHA;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ATUALIZACAO_USUARIO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.CADASTRO_CLIENTE;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_CREDENCIAIS_INVALIDAS;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_EMAIL_DUPLICADO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_LOGIN_DUPLICADO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_USUARIO_NAO_ENCONTRADO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_VALIDACAO_CADASTRO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_VALIDACAO_LOGIN;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_VALIDACAO_LOGIN_EM_BRANCO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.ERRO_VALIDACAO_SENHA;
import static br.com.kaique.techchallenge.config.SwaggerExamples.LISTA_USUARIOS;
import static br.com.kaique.techchallenge.config.SwaggerExamples.LOGIN;
import static br.com.kaique.techchallenge.config.SwaggerExamples.LOGIN_VALIDO;
import static br.com.kaique.techchallenge.config.SwaggerExamples.USUARIO_ATUALIZADO;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/usuarios")
@Tag(name = "Usuarios", description = "Operacoes para cadastro, consulta, atualizacao, remocao e login de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastrar usuario", description = "Cria um novo usuario no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Campos obrigatorios",
                                    value = ERRO_VALIDACAO_CADASTRO
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email ou login ja cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Email duplicado",
                                    value = ERRO_EMAIL_DUPLICADO
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Void> saveUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para cadastro do usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveUsuarioRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Cadastro de cliente",
                                    value = CADASTRO_CLIENTE
                            )
                    )
            )
            @Valid @RequestBody SaveUsuarioRequestDTO usuario
    ) {
        this.usuarioService.saveUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Listar usuarios", description = "Lista todos os usuarios ou filtra usuarios por nome.")
    @ApiResponse(
            responseCode = "200",
            description = "Usuarios encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Usuario.class)),
                    examples = @ExampleObject(
                            name = "Lista de usuarios",
                            value = LISTA_USUARIOS
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<Usuario>> findUsuario(
            @Parameter(description = "Nome usado para filtrar usuarios", example = "Joao Silva")
            @RequestParam(required = false) String nome
    ) {
        if (nome != null && !nome.isEmpty()) {
            return ResponseEntity.ok(usuarioService.findUsuarioByNome(nome));
        }
        return ResponseEntity.ok(usuarioService.findAllUsuarios());
    }

    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados cadastrais de um usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(
                                    name = "Usuario atualizado",
                                    value = USUARIO_ATUALIZADO
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Campo invalido",
                                    value = ERRO_VALIDACAO_LOGIN_EM_BRANCO
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email ou login ja cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Login duplicado",
                                    value = ERRO_LOGIN_DUPLICADO
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuario nao encontrado",
                                    value = ERRO_USUARIO_NAO_ENCONTRADO
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID do usuario", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para atualizacao do usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUsuarioRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Atualizacao de usuario",
                                    value = ATUALIZACAO_USUARIO
                            )
                    )
            )
            @Valid @RequestBody UpdateUsuarioRequestDTO updateUsuarioRequestDTO
    ) {

        return ResponseEntity.ok(usuarioService.updateUsuario(updateUsuarioRequestDTO, id));
    }

    @Operation(summary = "Atualizar senha", description = "Atualiza a senha de um usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Senha obrigatoria",
                                    value = ERRO_VALIDACAO_SENHA
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuario nao encontrado",
                                    value = ERRO_USUARIO_NAO_ENCONTRADO
                            )
                    )
            )
    })
    @PatchMapping("/{id}/senha")
    public ResponseEntity<Void> updateSenha(
            @Parameter(description = "ID do usuario", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nova senha do usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateSenhaUsuarioRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Atualizacao de senha",
                                    value = ATUALIZACAO_SENHA
                            )
                    )
            )
            @Valid @RequestBody UpdateSenhaUsuarioRequestDTO updateSenhaUsuarioRequestDTO
    ) {

        usuarioService.updateSenha(id, updateSenhaUsuarioRequestDTO.senha());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remover usuario", description = "Remove um usuario pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario removido com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuario nao encontrado",
                                    value = ERRO_USUARIO_NAO_ENCONTRADO
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID do usuario", example = "1")
            @PathVariable Long id
    ) {
        usuarioService.deleteUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Realizar login", description = "Valida as credenciais de login do usuario.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class),
                            examples = @ExampleObject(
                                    name = "Login valido",
                                    value = LOGIN_VALIDO
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Credenciais obrigatorias",
                                    value = ERRO_VALIDACAO_LOGIN
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Login ou senha incorretos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Credenciais invalidas",
                                    value = ERRO_CREDENCIAIS_INVALIDAS
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais do usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Login",
                                    value = LOGIN
                            )
                    )
            )
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        usuarioService.login(loginRequestDTO);
        return ResponseEntity.ok(true);
    }
}
