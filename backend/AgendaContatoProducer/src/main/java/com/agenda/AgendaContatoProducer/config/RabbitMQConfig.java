package com.agenda.AgendaContatoProducer.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value(value = "${rabbitmq.host}")
    private String hostName;

    @Value(value = "${rabbitmq.username}")
    private String username;

    @Value(value = "${rabbitmq.password}")
    private String password;

    @Value(value = "${rabbitmq.port}")
    private int port;

    public static final String USUARIO_EXCHANGE = "UsuarioExchange";
    public static final String USUARIO_ROUTING_KEY = "operacoes.usuario";

    @Bean
    public CachingConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostName);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        return connectionFactory;
    }

    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    // O Producer só precisa declarar a Exchange para garantir que ela exista ao enviar mensagens
    @Bean
    public DirectExchange usuarioExchange() {
        return new DirectExchange(USUARIO_EXCHANGE);
    }
}
