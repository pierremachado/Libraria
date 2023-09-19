package model;

public class Livro {

    public Livro(String titulo, String autor, String editora, String isbn, String anoPublicacao, String categoria, String id, int quantidadeDisponiveis) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.categoria = categoria;
        this.id = id;
        this.quantidadeDisponiveis = quantidadeDisponiveis;
    }

    private String titulo;

    private String autor;

    private String editora;

    private String isbn;

    private String anoPublicacao;

    private String categoria;

    private String id;

    private int quantidadeDisponiveis;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantidadeDisponiveis() {
        return quantidadeDisponiveis;
    }

    public void setQuantidadeDisponiveis(int quantidadeDisponiveis) {
        this.quantidadeDisponiveis = quantidadeDisponiveis;
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
                ", id='" + id + '\'' +
                ", quantidadeDisponiveis=" + quantidadeDisponiveis +
                '}';
    }
}
