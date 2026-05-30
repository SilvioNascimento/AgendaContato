package com.agenda.AgendaContatoProducer.producer;

import com.agenda.AgendaContatoProducer.config.RabbitMQConfig;
import com.agenda.AgendaContatoShared.payload.UsuarioPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuarioProducer {

    private final RabbitTemplate rabbitTemplate;

    public UsuarioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarEvento(UsuarioPayload usuarioPayload) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USUARIO_EXCHANGE,
                RabbitMQConfig.USUARIO_ROUTING_KEY,
                usuarioPayload
        );
    }
}
