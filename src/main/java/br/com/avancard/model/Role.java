package br.com.avancard.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "role_spring_rest_api")
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    private Long id;

    private String nomeRole;


    //MÉTODOS
    @Override
    public String getAuthority() { //Retorna o nome do acesso, autorização exemplo ROLE_GERENTE
        return this.nomeRole;
    }


    //MÉTODOS ESPECIAIS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeRole() {
        return nomeRole;
    }
    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }
}
