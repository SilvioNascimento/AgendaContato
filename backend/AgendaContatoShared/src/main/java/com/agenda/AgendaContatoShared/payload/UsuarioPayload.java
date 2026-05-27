package com.agenda.AgendaContatoShared.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioPayload {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "telefone")
    private String telefone;

    @JsonProperty(value = "senha")
    private String senha;

    @JsonProperty("tipo_acao")
    private String tipoAcao;
}
