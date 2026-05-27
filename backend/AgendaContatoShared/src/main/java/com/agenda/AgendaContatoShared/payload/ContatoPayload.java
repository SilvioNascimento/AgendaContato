package com.agenda.AgendaContatoShared.payload;

import com.agenda.AgendaContatoShared.enums.TipoContato;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoPayload {

    @JsonProperty(value = "id")
    private UUID id;

    @JsonProperty(value = "user_id")
    private UUID userId;

    @JsonProperty(value = "nome_contato")
    private String nomeContato;

    @JsonProperty(value = "email_contato")
    private String emailContato;

    @JsonProperty(value = "telefone_contato")
    private String telefoneContato;

    @JsonProperty(value = "tipo_contato")
    @Enumerated(EnumType.STRING)
    private TipoContato tipoContato;

    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty("tipo_acao")
    private String tipoAcao;
}
