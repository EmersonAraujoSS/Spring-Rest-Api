package br.com.avancard.security;

import br.com.avancard.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

// Estabelece o gerenciador de Token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    // Gerenciador de autenticação
    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        // Obriga a autenticar a URL
        super(new AntPathRequestMatcher(url));

        // Gerenciador de autenticacao
        setAuthenticationManager(authenticationManager);
    }

    // Retorna o usuário ao processar a autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // Pegando o token para validar
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        // Retorna o usuario login, senha e acessos
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        new JWTTokenAutenticacaoService().addAuthentication(response, auth.getName());
    }
}
