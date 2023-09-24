package model;

public class Bibliotecario extends Usuario{
    public Bibliotecario(String nome, String id, String cargo, String senha){
        super(nome, id, cargo, senha);
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
