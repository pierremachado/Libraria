package model;

public class Administrador extends Usuario{
    public Administrador(String nome, String id, String cargo, String senha){
        super(nome, id, cargo, senha);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                ", nome='" + getNome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                '}';
    }
}

