package main.java.libraria.dao.Livro;

import main.java.libraria.dao.CRUD;
import main.java.libraria.model.Livro;

import java.util.List;

public interface LivroDAO extends CRUD<Livro> {
    List<Livro> findBySearchKey(String key);
}
