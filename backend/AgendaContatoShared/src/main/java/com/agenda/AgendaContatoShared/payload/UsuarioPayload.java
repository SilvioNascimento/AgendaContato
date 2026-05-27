package com.agenda.AgendaContatoShared.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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

    @JsonProperty(value = "criado_em")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime criadoEm;

    @JsonProperty(value = "atualizado_em")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime atualizadoEm;

    @JsonProperty("tipo_acao")
    private String tipoAcao;
}
