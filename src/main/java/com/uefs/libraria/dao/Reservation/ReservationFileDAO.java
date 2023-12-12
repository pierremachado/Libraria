package main.java.com.uefs.libraria.dao.Reservation;

import main.java.com.uefs.libraria.model.Reservation;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;
import main.java.com.uefs.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ReservationFileDAO implements ReservationDAO {
    private final List<Reservation> reservationList;
    private final FileStorage fs;
    private int nextId = 1;

    public ReservationFileDAO() {
        this.fs = new FileStorage("reservation", "reservation");
        this.reservationList = fs.ler();
    }

    @Override
    public Reservation update(Reservation obj) {
        int index = this.reservationList.indexOf(obj);
        this.reservationList.set(index, obj);
        fs.salvar(reservationList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reservation reservation : this.reservationList) {
            if (reservation.getIdReserva().equals(id)) {
                this.reservationList.remove(reservation);
                fs.salvar(reservationList);
                return;
            }
        }
    }

    @Override
    public Reservation create(Reservation obj) {
        obj.setIdReserva(valueOf(nextId++));
        this.reservationList.add(obj);
        fs.salvar(reservationList);
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
        fs.salvar(reservationList);
    }

    public void deleteAllByBook(String idLivro) {
        this.reservationList.removeIf(reserva -> reserva.getIdLivro().equals(idLivro));
        fs.salvar(reservationList);
    }

    public void deleteAllByLeitor(String idLeitor) {
        this.reservationList.removeIf(reserva -> reserva.getIdLeitor().equals(idLeitor));
        fs.salvar(reservationList);
    }

    @Override
    public List<Reservation> findAll() {
        return this.reservationList;
    }
}