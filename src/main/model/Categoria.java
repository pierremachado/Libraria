package main.model;

public class Categoria {
    private String categoria;

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "categoria='" + categoria + '\'' +
                '}';
    }
}
