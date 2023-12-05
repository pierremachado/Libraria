package main.java.libraria.dao.Reserva;

import main.java.libraria.model.Reserva;
import main.java.libraria.model.enums.ReservaStatus;
import main.java.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ReservaFileDAO implements ReservaDAO {
    private final List<Reserva> reservaList;
    private final FileStorage fs;
    private int nextId = 1;

    public ReservaFileDAO() {
        this.fs = new FileStorage("Reserva", "Reserva");
        this.reservaList = fs.ler();
    }

    @Override
    public Reserva update(Reserva obj) {
        int index = this.reservaList.indexOf(obj);
        this.reservaList.set(index, obj);
        fs.salvar(reservaList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reserva reserva : this.reservaList) {
            if (reserva.getIdReserva().equals(id)) {
                this.reservaList.remove(reserva);
                fs.salvar(reservaList);
                return;
            }
        }
    }

    @Override
    public Reserva create(Reserva obj) {
        obj.setIdReserva(valueOf(nextId++));
        this.reservaList.add(obj);
        fs.salvar(reservaList);
        return obj;
    }

    @Override
    public Reserva findID(String id) {
        for (Reserva reserva : this.reservaList) {
            if (reserva.getIdReserva().equals(id))
                return reserva;
        }

        return null;
    }

    @Override
    public List<Reserva> findLivro(String idLivro) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.reservaList) {
            if (reserva.getIdLivro().equals(idLivro)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    @Override
    public List<Reserva> findLeitor(String idLeitor) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.reservaList) {
            if (reserva.getIdLeitor().equals(idLeitor)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    public List<Reserva> findCurrentLeitor(String idLeitor) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.reservaList) {
            if (reserva.getIdLeitor().equals(idLeitor) && (reserva.getStatus() == ReservaStatus.RESERVADO || reserva.getStatus() == ReservaStatus.LIBERADO)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    public void delete(Reserva reserva) {
        this.reservaList.remove(reserva);
        fs.salvar(reservaList);
    }

    public void deleteAllByBook(String idLivro) {
        this.reservaList.removeIf(reserva -> reserva.getIdLivro().equals(idLivro));
        fs.salvar(reservaList);
    }

    public void deleteAllByLeitor(String idLeitor) {
        this.reservaList.removeIf(reserva -> reserva.getIdLeitor().equals(idLeitor));
        fs.salvar(reservaList);
    }

    @Override
    public List<Reserva> findAll() {
        return this.reservaList;
    }
}