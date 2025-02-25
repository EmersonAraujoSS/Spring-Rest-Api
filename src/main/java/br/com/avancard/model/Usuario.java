package br.com.avancard.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "user_spring_rest_api")
public class Usuario implements UserDetails { //Serializable: Habilita a serialização de objetos, permitindo que seu estado
    static final long serialVersionUID = 1L;                      // seja convertido em bytes para armazenamento ou transmissão.


    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
    private String senha;
    private String nome;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Telefone> telefone = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_role", uniqueConstraints = @UniqueConstraint(
            columnNames = {"usuario_id","role_id"}, name = "unique_role_user"),
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario",
            foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)), inverseJoinColumns = @JoinColumn (name = "role_id",
            referencedColumnName = "id", table = "role", foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
    private List<Role> roles;

    //MÉTODOS ESPECIAIS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Telefone> getTelefone() {
        return telefone;
    }
    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
