package main.java.libraria.dao.Livro;

import main.java.libraria.model.Livro;
import main.java.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class LivroFileDAO implements LivroDAO {

    private final List<Livro> livroList;

    private final FileStorage fs;

    public LivroFileDAO() {
        this.fs = new FileStorage("Livro", "Livro");
        this.livroList = fs.ler();
    }

    @Override
    public Livro update(Livro obj) {
        int index = this.livroList.indexOf(obj);
        this.livroList.set(index, obj);
        this.fs.salvar(livroList);
        return obj;
    }

    @Override
    public void deleteID(String isbn) {
        for (Livro livro : this.livroList) {
            if (livro.getIsbn().equals(isbn)) {
                this.livroList.remove(livro);
                this.fs.salvar(livroList);
                return;
            }
        }
    }

    @Override
    public Livro create(Livro obj) {
        this.livroList.add(obj);
        this.fs.salvar(livroList);
        return obj;
    }

    @Override
    public Livro findID(String isbn) {
        for (Livro livro : this.livroList) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    public List<Livro> findBySearchKey(String key) {
        List<Livro> livrosPesquisados = new ArrayList<>();
        for (Livro livro : this.livroList) {
            if (livro.getChavePesquisa().contains(key.toLowerCase().replace(" ", ""))) {
                livrosPesquisados.add(livro);
            }
        }
        return livrosPesquisados;
    }

    @Override
    public List<Livro> findAll() {
        return this.livroList;
    }
}