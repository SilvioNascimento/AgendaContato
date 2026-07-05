package com.agenda.AgendaContatoShared.payload;

import com.agenda.AgendaContatoShared.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioPayload {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "telefone")
    private String telefone;

    @JsonProperty(value = "senha")
    private String senha;

    @JsonProperty(value = "cargo")
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @JsonProperty(value = "criado_em")
    private LocalDateTime criadoEm;

    @JsonProperty(value = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @JsonProperty("tipo_acao")
    private String tipoAcao;
}
