package main.java.libraria.dao.Livro;

import main.java.libraria.dao.CRUD;
import main.java.libraria.model.Livro;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public interface LivroDAO extends CRUD<Livro> {
    List<Livro> findBySearchKey(String key);
}
