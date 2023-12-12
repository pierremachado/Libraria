package main.java.com.uefs.libraria.dao.Book;

import main.java.com.uefs.libraria.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class BookListDAO implements BookDAO {

    private final List<Book> lista;

    public BookListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Book update(Book obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String isbn) {
        for (Book book : this.lista) {
            if (book.getIsbn().equals(isbn)) {
                this.lista.remove(book);
                return;
            }
        }
    }

    @Override
    public Book create(Book obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Book findID(String isbn) {
        for (Book book : this.lista) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findBySearchKey(String key) {
        List<Book> livrosPesquisados = new ArrayList<>();
        for (Book book : this.lista) {
            if (book.getChavePesquisa().contains(key.toLowerCase().replace(" ", ""))) {
                livrosPesquisados.add(book);
            }
        }
        return livrosPesquisados;
    }

    @Override
    public List<Book> findAll() {
        return this.lista;
    }
}