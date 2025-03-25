package br.com.avancard.services;

import br.com.avancard.model.Usuario;
import br.com.avancard.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    //ATRIBUTOS
    @Autowired
    private UsuarioRepository usuarioRepository;

    //MÉTODOS
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //consultar no banco o usuario
        Usuario usuario = usuarioRepository.findByUsuarioByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }

        return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
    }
}
