package main.dao.Emprestimo;

import main.model.Emprestimo;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class EmprestimoListDAO implements EmprestimoDAO {

    private List<Emprestimo> lista;
    private int nextId = 1;

    public EmprestimoListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getId().equals(id)) {
                this.lista.remove(emprestimo);
                return;
            }
        }
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setId(valueOf(nextId++));
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Emprestimo findID(String id) {
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getId().equals(id))
                return emprestimo;
        }

        return null;
    }

    @Override
    public List<Emprestimo> findAll() {
        return this.lista;
    }
}