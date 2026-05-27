package com.agenda.AgendaContatoShared.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoContato {

    PESSOA("Pessoa"),
    EMPRESA("Empresa");

    private String valor;

    TipoContato(String valor) {
        this.valor = valor;
    }

    @JsonValue  // Manda na API (JSON) -> "D", "I" ou "O"
    public String getValor() {
        return valor;
    }
}
