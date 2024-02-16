package com.uefs.libraria.dao.Book;

import com.uefs.libraria.dao.CRUD;
import com.uefs.libraria.model.Book;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public interface BookDAO extends CRUD<Book> {
    List<Book> findBySearchKey(String key);
}
