package com.agenda.AgendaContatoProducer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desativa o CSRF porque nossa API é Stateless (usa tokens, não cookies)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configura a sessão para não guardar nada em memória no servidor
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Define quais portas estão abertas e quais estão trancadas
                .authorizeHttpRequests(authorize -> authorize
                        // LIBERADO: Qualquer um pode criar usuário sem token
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // LIBERADO: Endpoint de login (quando você for implementar o POST de autenticação)
                        //.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // LIBERADO: Documentação do Swagger/SpringDoc para você testar as rotas visualmente
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // TRANCADO: Qualquer outra rota da API vai exigir o Token JWT no cabeçalho
                        .anyRequest().authenticated()
                )

                // 4. O SEGREDO: Injeta o seu filtro customizado na esteira do Spring Security.
                // Ele vai rodar ANTES do filtro padrão de usuário e senha, interceptando o token.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean necessário para criptografar as senhas no seu UserService antes de enviar para a fila do RabbitMQ
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
