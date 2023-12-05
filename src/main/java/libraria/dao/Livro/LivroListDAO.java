package main.java.libraria.dao.Livro;

import main.java.libraria.model.Livro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class LivroListDAO implements LivroDAO {

    private final List<Livro> lista;

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
    public void deleteID(String isbn) {
        for (Livro livro : this.lista) {
            if (livro.getIsbn().equals(isbn)) {
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
    public Livro findID(String isbn) {
        for (Livro livro : this.lista) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    public List<Livro> findBySearchKey(String key) {
        List<Livro> livrosPesquisados = new ArrayList<>();
        for (Livro livro : this.lista) {
            if (livro.getChavePesquisa().contains(key.toLowerCase().replace(" ", ""))) {
                livrosPesquisados.add(livro);
            }
        }
        return livrosPesquisados;
    }

    @Override
    public List<Livro> findAll() {
        return this.lista;
    }
}