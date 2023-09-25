package main.model;

import java.util.List;
import java.util.ArrayList;

public class Bibliotecario extends Usuario{
    private List<Emprestimo> emprestimoList;
    public Bibliotecario(String nome, String id, String cargo, String senha){
        super(nome, id, cargo, senha);
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
