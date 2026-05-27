package com.agenda.AgendaContatoShared.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Cargo {

    ADMIN("Admin"),
    USUARIO("Usuario");

    private String valor;

    Cargo(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }
}
