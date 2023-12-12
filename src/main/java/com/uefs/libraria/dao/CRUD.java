package main.java.com.uefs.libraria.dao;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public interface CRUD<T> {

    T update(T obj);

    void deleteID(String id);

    T create(T obj);

    T findID(String id);

    List<T> findAll();
}
