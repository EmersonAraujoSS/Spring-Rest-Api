package br.com.avancard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// Filtro onde todas as requisições serão capturadas para autenticar
public class JWTApiAutenticacaoFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Estabelece a autenticação para a requisição
        Authentication authentication = new JWTTokenAutenticacaoService().getAuthentication((HttpServletRequest) request);

        //Coloca o processo de autenticação no spring securiry
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Continua o processo
        chain.doFilter(request, response);

    }
}
