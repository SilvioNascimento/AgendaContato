package com.agenda.AgendaContatoConsumer.consumer;

import com.agenda.AgendaContatoConsumer.model.Usuario;
import com.agenda.AgendaContatoConsumer.repository.UsuarioRepository;
import com.agenda.AgendaContatoShared.payload.UsuarioPayload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsuarioConsumer {

    private final UsuarioRepository usuarioRepository;

    public UsuarioConsumer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @RabbitListener(containerFactory = "listenerContainerFactory", queues = "UsuarioQueue")
    public void escutarOperacaoUsuario(UsuarioPayload payload) {
        System.out.println(">>> [Fila] Mensagem capturada para o usuário: " + payload.getUsername());

        if (payload.getTipoAcao().equals("CREATE")) {
            Usuario usuario = new Usuario();
            usuario.setUsername(payload.getUsername());
            usuario.setEmail(payload.getEmail());
            usuario.setTelefone(payload.getTelefone());
            usuario.setSenha(payload.getSenha());
            usuario.setCargo(payload.getCargo());
            usuario.setCriadoEm(payload.getCriadoEm());

//            LocalDateTime dataHoraAtualizada = payload.getAtualizadoEm() == null
//                    ? null
//                    : payload.getAtualizadoEm();

            usuario.setAtualizadoEm(null);

            usuarioRepository.save(usuario);
            System.out.println(">>> [Postgres] Usuário salvo no banco com sucesso!");
        }
    }
}
