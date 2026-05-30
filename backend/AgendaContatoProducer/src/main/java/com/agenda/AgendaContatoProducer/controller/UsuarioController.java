package com.agenda.AgendaContatoProducer.controller;

import com.agenda.AgendaContatoProducer.service.UsuarioService;
import com.agenda.AgendaContatoShared.dto.usuario.UsuarioCriadoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String criarUsuario(@Valid @RequestBody UsuarioCriadoDTO dto) {
        usuarioService.solicitarCadastro(dto);
        return "Solicitação de cadastro enviada para a fila de processamento!";
    }
}
