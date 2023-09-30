package main.java.libraria.dao.Reserva;

import main.java.libraria.model.Reserva;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ReservaListDAO implements ReservaDAO {

    private List<Reserva> lista;
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
    public List<Reserva> findAll() {
        return this.lista;
    }
}