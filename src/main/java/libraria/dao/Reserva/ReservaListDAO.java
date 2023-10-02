package main.java.libraria.dao.Reserva;

import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Reserva;
import main.java.libraria.model.enums.ReservaStatus;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ReservaListDAO implements ReservaDAO {

    private final List<Reserva> lista;
    private int nextId = 1;

    public ReservaListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Reserva update(Reserva obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reserva reserva : this.lista) {
            if (reserva.getId().equals(id)) {
                this.lista.remove(reserva);
                return;
            }
        }
    }

    @Override
    public Reserva create(Reserva obj) {
        obj.setId(valueOf(nextId++));
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Reserva findID(String id) {
        for (Reserva reserva : this.lista) {
            if (reserva.getId().equals(id))
                return reserva;
        }

        return null;
    }

    @Override
    public List<Reserva> findLivro(Livro livro) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.lista) {
            if (reserva.getLivro().equals(livro)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    @Override
    public List<Reserva> findLeitor(Leitor leitor) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.lista) {
            if (reserva.getLeitor().equals(leitor)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    public List<Reserva> findCurrentLeitor(Leitor leitor) {
        ArrayList<Reserva> reservaArrayList = new ArrayList<Reserva>();
        for (Reserva reserva : this.lista) {
            if (reserva.getLeitor().equals(leitor) && (reserva.getStatus() == ReservaStatus.ESPERA || reserva.getStatus() == ReservaStatus.LIBERADO)) {
                reservaArrayList.add(reserva);
            }
        }
        return reservaArrayList;
    }

    public void delete(Reserva reserva) {
        this.lista.remove(reserva);
    }

    public void deleteAllByBook(Livro livro) {
        this.lista.removeIf(reserva -> reserva.getLivro().equals(livro));
    }

    public void deleteAllByLeitor(Leitor leitor) {
        this.lista.removeIf(reserva -> reserva.getLeitor().equals(leitor));
    }

    @Override
    public List<Reserva> findAll() {
        return this.lista;
    }
}