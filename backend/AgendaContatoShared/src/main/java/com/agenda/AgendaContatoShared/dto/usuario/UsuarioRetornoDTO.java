package com.agenda.AgendaContatoShared.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRetornoDTO {

    @NotNull(message = "Campo username não pode ser null!")
    private String username;

    @NotNull(message = "Campo email não pode ser null!")
    private String email;

    @NotNull(message = "Campo telefone não pode ser null!")
    private String telefone;

    @NotNull(message = "Campo senha não pode ser null!")
    private String senha;

    @NotNull(message = "Campo criadoEm não pode ser null!")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
