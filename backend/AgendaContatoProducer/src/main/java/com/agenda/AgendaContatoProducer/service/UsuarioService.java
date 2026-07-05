package com.agenda.AgendaContatoProducer.service;

import com.agenda.AgendaContatoProducer.producer.UsuarioProducer;
import com.agenda.AgendaContatoShared.dto.usuario.UsuarioCriadoDTO;
import com.agenda.AgendaContatoShared.enums.Cargo;
import com.agenda.AgendaContatoShared.payload.UsuarioPayload;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    private final UsuarioProducer usuarioProducer;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioProducer usuarioProducer, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioProducer = usuarioProducer;
        this.passwordEncoder = passwordEncoder;
    }

    public void solicitarCadastro(UsuarioCriadoDTO dto) {
        // 1. Instancia o Payload do Shared
        UsuarioPayload payload = new UsuarioPayload();

        // 2. Transfere os dados recebidos pelo HTTP para o Payload
        payload.setUsername(dto.getUsername());
        payload.setEmail(dto.getEmail());
        payload.setTelefone(dto.getTelefone());
        payload.setCargo(Cargo.USUARIO);

        // 3. Criptografa a senha para que ela viaje segura e mascarada pela fila
        String senhaMascarada = passwordEncoder.encode(dto.getSenha());
        payload.setSenha(senhaMascarada);

        payload.setCriadoEm(LocalDateTime.now());

        // 4. Carimba o tipo de ação para guiar o consumidor
        payload.setTipoAcao("CREATE");

        // 5. Envia para o RabbitMQ
        usuarioProducer.publicarEvento(payload);
    }
}
