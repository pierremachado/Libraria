package com.uefs.libraria.dao.Book;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class BookFileDAO implements BookDAO {

    private final List<Book> bookList;

    private final FileStorage fs;

    public BookFileDAO() {
        this.fs = new FileStorage("book", "book");
        this.bookList = fs.read();
    }

    @Override
    public Book update(Book obj) {
        int index = this.bookList.indexOf(obj);
        this.bookList.set(index, obj);
        this.fs.save(bookList);
        return obj;
    }

    @Override
    public void deleteID(String isbn) {
        for (Book book : this.bookList) {
            if (book.getIsbn().equals(isbn)) {
                this.bookList.remove(book);
                this.fs.save(bookList);
                return;
            }
        }
    }

    @Override
    public Book create(Book obj) {
        this.bookList.add(obj);
        this.fs.save(bookList);
        return obj;
    }

    @Override
    public Book findID(String isbn) {
        for (Book book : this.bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findBySearchKey(String key) {
        List<Book> livrosPesquisados = new ArrayList<>();
        for (Book book : this.bookList) {
            if (book.getChavePesquisa().contains(key.toLowerCase().replace(" ", ""))) {
                livrosPesquisados.add(book);
            }
        }
        return livrosPesquisados;
    }

    @Override
    public List<Book> findAll() {
        return this.bookList;
    }
}