package br.com.kaique.techchallenge.dtos;

import br.com.kaique.techchallenge.entities.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUsuarioRequestDTO(
        @NotBlank
        String nome,
        @NotBlank
        String endereco,
        @NotBlank
        String email,
        @NotBlank
        String login,
        @NotNull
        TipoUsuario tipoUsuario
) {
}
