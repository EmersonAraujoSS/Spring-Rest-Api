package br.com.avancard.security;

import br.com.avancard.ApplicationContextLoad;
import br.com.avancard.model.Usuario;
import br.com.avancard.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {

    // Tempo de validade do token (esse tempo é 2 dias em milissegundos)
    private static final long EXPIRATION_TIME = 172800000;
    // Chave secreta para assinar o token (deve ser longa e segura)
    private static final String SECRET = "SenhaExtremamenteSecretaComPeloMenos32Caracteres";
    // Prefixo padrão de token
    private static final String TOKEN_PREFIX = "Bearer ";
    // Cabeçalho do token
    private static final String HEADER_STRING = "Authorization";


    // Método para gerar e adicionar o token na resposta
    public void addAuthentication(HttpServletResponse response, String username) throws IOException {
        // Criando a chave secreta
        Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        // Gerando o token JWT
        String jwt = Jwts.builder() // chama o gerador de token
                .subject(username) // adiciona o usuario
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // tempo de expiracao
                .signWith(secretKey) // assinando com a chave
                .compact(); // compactação e algoritmos de geração de senha
        // junta o token com o prefixo
        String token = TOKEN_PREFIX + " " + jwt;
        // Adicionando o token no cabeçalho da resposta HTTP
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        // escreve o token como resposta no corpo http
        response.getWriter().println("{\"Authorization\": \""+token+"\"}");
    }

    // Método que retorna o usuário validado com o ‘token’ ou caso seja inválido, retorna null
    public Authentication getAuthentication(HttpServletRequest request) {
        // Obtém o token do cabeçalho da requisição
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                // Criando a chave secreta correta
                Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

                // Extraindo as claims do token
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                // Obtém o usuário do token
                String user = claims.getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findByUsuarioByLogin(user);

                    // retornar o usuário logado
                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
                    }
                }
        }catch(Exception e){
            return null; // Token inválido ou expirado
        }
    }
        return null; // Se não houver ‘token’ ou for inválido
    }
}
