package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

import java.util.List;
import java.util.ArrayList;

public class Bibliotecario extends Usuario{
    private List<Emprestimo> emprestimoList;
    public Bibliotecario(String nome, String sobrenome, String id, String senha){
        super(nome, sobrenome, id, "Bibliotecário", senha, UserPermissao.BIBLIOTECARIO);
        this.emprestimoList = new ArrayList<>();
    }

    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    @Override
    public String toString() {
        return "Bibliotecario{" +
                ", nome='" + getNome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                '}';
    }
}
