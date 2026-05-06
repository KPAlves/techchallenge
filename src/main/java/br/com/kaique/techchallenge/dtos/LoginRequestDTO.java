package br.com.kaique.techchallenge.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
