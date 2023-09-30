package main.java.libraria.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {

    private String titulo;

    private String autor;

    private String editora;

    private String isbn;

    private String anoPublicacao;
    private String chavePesquisa;

    private List<Categoria> categoria;

    private int quantidadeDisponiveis;

    private int vezesPesquisado;

    public Livro(String titulo, String autor, String editora, String isbn, String anoPublicacao, List<Categoria> categoria, int quantidadeDisponiveis, int vezesPesquisado) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.categoria = categoria;
        this.quantidadeDisponiveis = quantidadeDisponiveis;
        this.vezesPesquisado = vezesPesquisado;
        this.chavePesquisa = (titulo + " " + autor + " " + editora + " " + isbn).replace(" ", "").toLowerCase();
    }

    public Livro(String titulo, String autor, String editora, String isbn, String anoPublicacao, int quantidadeDisponiveis, int vezesPesquisado) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponiveis = quantidadeDisponiveis;
        this.vezesPesquisado = vezesPesquisado;
        this.categoria = new ArrayList<>();
        this.chavePesquisa = (titulo + " " + autor + " " + editora + " " + isbn).replace(" ", "").toLowerCase();
    }

    public void aumentarPesquisa(){
        this.vezesPesquisado++;
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

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }

    public int getVezesPesquisado() {
        return vezesPesquisado;
    }

    public void setVezesPesquisado(int vezesPesquisado) {
        this.vezesPesquisado = vezesPesquisado;
    }

    public String getChavePesquisa() {
        return chavePesquisa;
    }

    public void updateChavePesquisa() {
        this.chavePesquisa = (this.titulo + this.autor + this.isbn).replace(" ", "").toLowerCase();
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
