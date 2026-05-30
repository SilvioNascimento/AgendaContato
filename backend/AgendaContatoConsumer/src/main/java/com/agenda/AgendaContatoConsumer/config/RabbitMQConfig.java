package com.agenda.AgendaContatoConsumer.config;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

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

    // --- CONSTANTES CENTRALIZADAS (Altere aqui e muda no sistema todo) ---
    public static final String USUARIO_EXCHANGE = "UsuarioExchange";

    // Rotas (Routing Keys)
    public static final String ROUTING_KEY_USUARIO = "operacoes.usuario";
    public static final String PREFIXO_ERRO = "boq.";
    public static final String ROUTING_KEY_ERRO_USUARIO = PREFIXO_ERRO + ROUTING_KEY_USUARIO; // "boq.operacoes.usuario"

    // Filas (Queues)
    public static final String QUEUE_USUARIO = "UsuarioQueue";
    public static final String QUEUE_ERRO_USUARIO = "boq.UsuarioQueue";

    // Conexão física com o servidor (Broker) do RabbitMQ
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

    // Contâiner responsável por consumir mensagens na fila (Queue)
    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(
            ConnectionFactory connectionFactory,
            RetryOperationsInterceptor retryOperationsInterceptor
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(retryOperationsInterceptor);
        factory.setMessageConverter(converter());
        return factory;
    }

    // Cria um Administrador no servidor (Broker) do RabbitMQ
    @Bean
    public AmqpAdmin amqpAdmin() throws Exception {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public DirectExchange usuarioExchange() {
        return new DirectExchange(USUARIO_EXCHANGE);
    }

    // Fila Normal
    @Bean
    public Queue usuarioQueue() {
        return new Queue(QUEUE_USUARIO, true);
    }

    // Fila de Erro (DLQ)
    @Bean
    public Queue boqUsuarioQueue() {
        return new Queue(QUEUE_ERRO_USUARIO, true);
    }

    // Binding da Fila Normal usando as constantes
    @Bean
    public Binding bindingUsuario(Queue usuarioQueue, DirectExchange usuarioExchange) {
        return BindingBuilder.bind(usuarioQueue).to(usuarioExchange).with(ROUTING_KEY_USUARIO);
    }

    // Binding da Fila de Erro usando as constantes
    @Bean
    public Binding bindingBoqUsuario(Queue boqUsuarioQueue, DirectExchange usuarioExchange) {
        return BindingBuilder.bind(boqUsuarioQueue).to(usuarioExchange).with(ROUTING_KEY_ERRO_USUARIO);
    }

    // Focado na resiliência e tratamento de falhas.
    // Caso uma mensagem falha em ser consumida, ela sai da fila em que estava e se direciona em outra
    // rota para uma Dead Letter Queue (DLQ).
    @Bean
    public RepublishMessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(rabbitTemplate);
        recoverer.setErrorRoutingKeyPrefix(PREFIXO_ERRO);
        return recoverer;
    }

    /**
     * Define que o mecanismo de repetição não manterá um estado complexo entre as tentativas. É a forma
     * mais comum e leve de lidar com erros em filas, onde cada tentativa de reprocessamento é tratada
     * como um evento independente.
     */
    @Bean
    public RetryOperationsInterceptor retryOperationsInterceptor(
            RepublishMessageRecoverer recoverer
    ) {
        RetryOperationsInterceptor interceptor = RetryInterceptorBuilder
                .stateless()
                .maxAttempts(2) // Repete a ação 2 vezes
                .backOffOptions(2000, 1, 100000)
                .recoverer(recoverer)
                .build();

        return interceptor;
    }

}
