package main.java.libraria.dao.Emprestimo;

import main.java.libraria.model.Emprestimo;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class EmprestimoListDAO implements EmprestimoDAO {

    private final List<Emprestimo> lista;
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
            if (emprestimo.getIdEmprestimo().equals(id)) {
                this.lista.remove(emprestimo);
                return;
            }
        }
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setIdEmprestimo(valueOf(nextId++));
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Emprestimo findID(String id) {
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getIdEmprestimo().equals(id))
                return emprestimo;
        }

        return null;
    }

    @Override
    public List<Emprestimo> findIdLivro(String idLivro) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getIdLivro().equals(idLivro)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findIdOperador(String idOperador) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getIdOperador().equals(idOperador)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findIdLeitor(String idLeitor) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getIdLeitor().equals(idLeitor)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findAll() {
        return this.lista;
    }
}