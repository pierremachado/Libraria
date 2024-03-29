package com.uefs.libraria.dao.Reservation;

import com.uefs.libraria.dao.CRUD;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.User;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public interface ReservationDAO extends CRUD<Reservation> {
    List<Reservation> findLivro(String idLivro);

    List<Reservation> findLeitor(String idLeitor);

    List<Reservation> findCurrentLeitor(String idLeitor);

    void delete(Reservation reservation);

    void deleteAllByBook(String idLivro);

    void deleteAllByLeitor(String idLeitor);

    void updateReservationId(User userToEdit, String newId);

    void updateReservationIsbn(Book bookToEdit, String newIsbn);
}
