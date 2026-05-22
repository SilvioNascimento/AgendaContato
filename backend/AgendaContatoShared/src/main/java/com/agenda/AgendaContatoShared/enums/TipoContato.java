package com.agenda.AgendaContatoShared.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoContato {
    PESSOA("Pessoa"),
    EMPRESA("Empresa");

    private String texto;

    TipoContato(String texto) {
        this.texto = texto;
    }

    @JsonValue  // Manda na API (JSON) -> "D", "I" ou "O"
    public String getTexto() {
        return texto;
    }
}
