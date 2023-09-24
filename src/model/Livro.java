package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Livro {

    private String titulo;

    private String autor;

    private String editora;

    private String isbn;

    private String anoPublicacao;

    private List<String> categoria;

    private int quantidadeDisponiveis;

    private int vezesPesquisado;

    public Livro(String titulo, String autor, String editora, String isbn, String anoPublicacao, List<String> categoria, int quantidadeDisponiveis, int vezesPesquisado) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.categoria = categoria;
        this.quantidadeDisponiveis = quantidadeDisponiveis;
        this.vezesPesquisado = vezesPesquisado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidadeDisponiveis() {
        return quantidadeDisponiveis;
    }

    public void setQuantidadeDisponiveis(int quantidadeDisponiveis) {
        this.quantidadeDisponiveis = quantidadeDisponiveis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(isbn, livro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anoPublicacao='" + anoPublicacao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", quantidadeDisponiveis=" + quantidadeDisponiveis +
                '}';
    }
}
