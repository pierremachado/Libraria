package main.java.libraria.dao.Emprestimo;

import main.java.libraria.model.Emprestimo;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
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
    public List<Emprestimo> findLivro(Livro livro) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getLivro().equals(livro)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findUsuario(Usuario usuario) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getUsuario().equals(usuario)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findLeitor(Leitor leitor) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.lista) {
            if (emprestimo.getLeitor().equals(leitor)) {
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