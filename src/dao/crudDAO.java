package dao;

import java.util.List;

public interface crudDAO<T>{

    T update(T obj);
    void deleteID(String id);
    T create(T obj);
    T findID(String id);
    List<T> findall();
}
