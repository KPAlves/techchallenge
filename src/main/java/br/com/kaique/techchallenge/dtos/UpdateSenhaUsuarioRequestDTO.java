package br.com.kaique.techchallenge.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateSenhaUsuarioRequestDTO(

        @NotBlank
        String senha
) {
}
