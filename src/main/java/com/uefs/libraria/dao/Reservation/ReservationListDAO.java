package com.uefs.libraria.dao.Reservation;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.User;
import com.uefs.libraria.model.enums.ReservationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uefs.libraria.dao.DAO.getReservaDAO;
import static java.lang.String.valueOf;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class ReservationListDAO implements ReservationDAO {

    private final List<Reservation> reservationList;
    private int nextId = 1;

    public ReservationListDAO() {
        this.reservationList = new ArrayList<>();
    }

    @Override
    public Reservation update(Reservation obj) {
        int index = this.reservationList.indexOf(obj);
        this.reservationList.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdReserva().equals(id)) {
                this.reservationList.remove(reservation);
                return;
            }
        }
    }

    @Override
    public void updateReservationId(User userToEdit, String newId){
        for(Reservation reservation : this.reservationList){
            if (Objects.equals(reservation.getIdLeitor(), userToEdit.getId())){
                reservation.setIdLeitor(newId);
            }
        }
    }

    @Override
    public void updateReservationIsbn(Book bookToEdit, String newIsbn){
        for(Reservation reservation : getReservaDAO().findAll()){
            if (Objects.equals(reservation.getIdLivro(), bookToEdit.getIsbn())){
                reservation.setIdLivro(newIsbn);
            }
        }
    }

    @Override
    public Reservation create(Reservation obj) {
        obj.setIdReserva(valueOf(nextId++));
        this.reservationList.add(obj);
        return obj;
    }

    @Override
    public Reservation findID(String id) {
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdReserva().equals(id))
                return reservation;
        }

        return null;
    }

    @Override
    public List<Reservation> findLivro(String idLivro) {
        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdLivro().equals(idLivro)) {
                reservationArrayList.add(reservation);
            }
        }
        return reservationArrayList;
    }

    @Override
    public List<Reservation> findLeitor(String idLeitor) {
        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdLeitor().equals(idLeitor)) {
                reservationArrayList.add(reservation);
            }
        }
        return reservationArrayList;
    }

    public List<Reservation> findCurrentLeitor(String idLeitor) {
        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdLeitor().equals(idLeitor) && (reservation.getStatus() == ReservationStatus.RESERVADO || reservation.getStatus() == ReservationStatus.LIBERADO)) {
                reservationArrayList.add(reservation);
            }
        }
        return reservationArrayList;
    }

    public void delete(Reservation reservation) {
        this.reservationList.remove(reservation);
    }

    public void deleteAllByBook(String idLivro) {
        this.reservationList.removeIf(reserva -> reserva.getIdLivro().equals(idLivro));
    }

    public void deleteAllByLeitor(String idLeitor) {
        this.reservationList.removeIf(reserva -> reserva.getIdLeitor().equals(idLeitor));
    }

    @Override
    public List<Reservation> findAll() {
        return this.reservationList;
    }
}