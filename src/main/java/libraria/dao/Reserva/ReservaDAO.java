package main.java.libraria.dao.Reserva;

import main.java.libraria.dao.CRUD;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Reserva;

import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public interface ReservaDAO extends CRUD<Reserva> {
    List<Reserva> findLivro(Livro livro);

    List<Reserva> findLeitor(Leitor leitor);

    List<Reserva> findCurrentLeitor(Leitor leitor);

    void delete(Reserva reserva);

    void deleteAllByBook(Livro livro);

    void deleteAllByLeitor(Leitor leitor);
}
