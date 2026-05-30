package com.agenda.AgendaContatoProducer.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 1. Extrai o Token do cabeçalho HTTP
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println(">>> [JWT] Username extraído com sucesso: " + username);
            } catch (Exception e) {
                System.out.println(">>> [JWT] Falha ao extrair username do token: " + e.getMessage());
            }
        }

        // 2. Se o token tem um usuário e a requisição ainda não foi autenticada no contexto atual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3. Validamos matematicamente se o token é legítimo e se não expirou
            if (jwtUtil.isTokenExpired(jwt) == false) {

                // 4. Extraímos as Roles/Permissões salvas dentro do próprio Token!
                // O seu jwtUtil deve conseguir ler a claim "roles" e transformar em GrantedAuthority
                List<GrantedAuthority> authorities = jwtUtil.extractAuthorities(jwt);

                // 5. Criamos o objeto de autenticação direto na memória, usando as informações do Token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username, // Principal (Quem é o usuário)
                        null,     // Credentials (Senha não é necessária aqui, ele já está logado!)
                        authorities // As permissões que vieram no JWT (Ex: ROLE_USER, ROLE_ADMIN)
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Coloca o usuário como autenticado no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(">>> [Security] Usuário autenticado SEM BANCO DE DADOS: " + username);
            }
        }

        chain.doFilter(request, response);
    }
}