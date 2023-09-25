package main.dao.Livro;

import main.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroListDAO implements LivroDAO {

    private List<Livro> lista;

    public LivroListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Livro update(Livro obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Livro livro : this.lista) {
            if (livro.getIsbn().equals(id)) {
                this.lista.remove(livro);
                return;
            }
        }
    }

    @Override
    public Livro create(Livro obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Livro findID(String id) {
        for (Livro livro : this.lista) {
            if (livro.getIsbn().equals(id))
                return livro;
        }

        return null;
    }

    @Override
    public List<Livro> findAll() {
        return this.lista;
    }
}