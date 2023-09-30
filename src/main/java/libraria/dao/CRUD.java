package main.java.libraria.dao;

import java.util.List;

public interface CRUD<T>{

    T update(T obj);
    void deleteID(String id);
    T create(T obj);
    T findID(String id);
    List<T> findAll();
}
