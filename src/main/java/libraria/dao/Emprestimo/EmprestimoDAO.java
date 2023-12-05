package main.java.libraria.dao.Emprestimo;

import main.java.libraria.dao.CRUD;
import main.java.libraria.model.Emprestimo;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Usuario;

import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public interface EmprestimoDAO extends CRUD<Emprestimo> {
    List<Emprestimo> findIdLivro(String idLivro);

    List<Emprestimo> findIdOperador(String idOperador);

    List<Emprestimo> findIdLeitor(String idLeitor);
}
