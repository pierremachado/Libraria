package main.java.libraria.model;

import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.UserPermissao;

import java.util.ArrayList;
import java.util.List;

public class Leitor extends Usuario{
    private String endereco;
    private String telefone;
    private LeitorStatus status;
    private List<Reserva> reservaList;
    private List<Emprestimo> emprestimoList;

    public Leitor(String nome, String sobrenome, String id, String senha, String endereco, String telefone, LeitorStatus Status){
        super(nome, sobrenome, id, "Leitor", senha, UserPermissao.LEITOR);
        this.endereco = endereco;
        this.telefone = telefone;
        this.status = Status;
        this.reservaList = new ArrayList<>();
        this.emprestimoList = new ArrayList<>();
    }

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public LeitorStatus getStatus(){
        return status;
    }

    public void setStatus(LeitorStatus status){
        this.status = status;
    }

    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    @Override
    public String toString() {
        return "Leitor{" +
                ", nome='" + getNome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
