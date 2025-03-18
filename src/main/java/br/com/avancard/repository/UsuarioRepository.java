package br.com.avancard.repository;

import br.com.avancard.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u where u.login = ?1")
    Usuario findByUsuarioByLogin(String login);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update usuario set token = ?1 where login = ?2")
    void atualizarTokenUser(String token, String login);

}
