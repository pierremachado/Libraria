package main.dao.Leitor;

import main.model.Leitor;

import java.util.ArrayList;
import java.util.List;

public class LeitorListDAO implements LeitorDAO{

    private List<Leitor> lista;

    public LeitorListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Leitor update(Leitor obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Leitor leitor : this.lista) {
            if (leitor.getId().equals(id)) {
                this.lista.remove(leitor);
                return;
            }
        }
    }

    @Override
    public Leitor create(Leitor obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Leitor findID(String id) {
        for (Leitor leitor : this.lista) {
            if (leitor.getId().equals(id))
                return leitor;
        }

        return null;
    }

    @Override
    public List<Leitor> findAll() {
        return this.lista;
    }
}