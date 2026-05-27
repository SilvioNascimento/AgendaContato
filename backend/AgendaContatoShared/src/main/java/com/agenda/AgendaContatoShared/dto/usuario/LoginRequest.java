package com.agenda.AgendaContatoShared.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Campo email é obrigatório!")
        String email,

        @NotBlank(message = "Campo senha é obrigatório!")
        String senha
) {
}
