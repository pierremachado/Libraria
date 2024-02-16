package com.uefs.libraria.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Book implements Serializable {
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private Year anoPublicacao;
    private String chavePesquisa;
    private String categoria;
    private int quantidadeDisponiveis;
    private int vezesPesquisado;

    public Book(String titulo, String autor, String editora, String isbn, Year anoPublicacao, String categoria, int quantidadeDisponiveis, int vezesPesquisado) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponiveis = quantidadeDisponiveis;
        this.vezesPesquisado = vezesPesquisado;
        this.categoria = categoria;
        this.chavePesquisa = (titulo + autor + categoria + editora + isbn).replace(" ", "").toLowerCase();
    }

    public void aumentarPesquisa() {
        this.vezesPesquisado++;
    }

    public void aumentarQuantidade(int quantidade) {
        this.quantidadeDisponiveis += quantidade;
    }

    public void reduzirQuantidade(int quantidade) {
        this.quantidadeDisponiveis -= quantidade;
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

    public Year getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Year anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getQuantidadeDisponiveis() {
        return quantidadeDisponiveis;
    }

    public void setQuantidadeDisponiveis(int quantidadeDisponiveis) {
        this.quantidadeDisponiveis = quantidadeDisponiveis;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
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

    public void setChavePesquisa() {
        this.chavePesquisa = (titulo + autor + categoria + editora + isbn).replace(" ", "").toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
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
