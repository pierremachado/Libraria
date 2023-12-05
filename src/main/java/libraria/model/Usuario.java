package main.java.libraria.model;

import main.java.libraria.dao.DAO;
import main.java.libraria.model.enums.UserPermissao;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public abstract class Usuario implements Serializable {
    private String nome;
    private String sobrenome;
    private String id;
    private String cargo;
    private String senha;
    private UserPermissao permissao;

    public Usuario(String nome, String sobrenome, String id, String cargo, String senha, UserPermissao permissao) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id = id;
        this.cargo = cargo;
        this.senha = senha;
        this.permissao = permissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserPermissao getPermissao() {
        return permissao;
    }

    public void setPermissao(UserPermissao permissao) {
        this.permissao = permissao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && permissao == usuario.permissao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissao);
    }
}