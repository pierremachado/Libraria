package main.java.libraria.dao.Categoria;

import main.java.libraria.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaListDAO implements CategoriaDAO {

    private List<Categoria> lista;

    public CategoriaListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Categoria update(Categoria obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String genero) {
        for (Categoria categoria : this.lista) {
            if (categoria.getCategoria().equals(genero)) {
                this.lista.remove(categoria);
                return;
            }
        }
    }

    @Override
    public Categoria create(Categoria obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Categoria findID(String genero) {
        for (Categoria categoria : this.lista) {
            if (categoria.getCategoria().equals(genero))
                return categoria;
        }

        return null;
    }

    @Override
    public List<Categoria> findAll() {
        return this.lista;
    }
}