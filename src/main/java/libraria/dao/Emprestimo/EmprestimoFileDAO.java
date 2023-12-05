package main.java.libraria.dao.Emprestimo;

import main.java.libraria.dao.Bibliotecario.BibliotecarioDAO;
import main.java.libraria.model.Bibliotecario;
import main.java.libraria.model.Emprestimo;
import main.java.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class EmprestimoFileDAO implements EmprestimoDAO {
    private final List<Emprestimo> emprestimoList;
    private final FileStorage fs;
    private int nextId = 1;

    public EmprestimoFileDAO() {
        this.fs = new FileStorage("Emprestimo", "Emprestimo");
        this.emprestimoList = fs.ler();
    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        int index = this.emprestimoList.indexOf(obj);
        this.emprestimoList.set(index, obj);
        fs.salvar(emprestimoList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Emprestimo emprestimo : this.emprestimoList) {
            if (emprestimo.getIdEmprestimo().equals(id)) {
                this.emprestimoList.remove(emprestimo);
                fs.salvar(emprestimoList);
                return;
            }
        }
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setIdEmprestimo(valueOf(nextId++));
        this.emprestimoList.add(obj);
        fs.salvar(emprestimoList);
        return obj;
    }

    @Override
    public Emprestimo findID(String id) {
        for (Emprestimo emprestimo : this.emprestimoList) {
            if (emprestimo.getIdEmprestimo().equals(id))
                return emprestimo;
        }

        return null;
    }

    @Override
    public List<Emprestimo> findIdLivro(String idLivro) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.emprestimoList) {
            if (emprestimo.getIdLivro().equals(idLivro)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findIdOperador(String idOperador) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.emprestimoList) {
            if (emprestimo.getIdOperador().equals(idOperador)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findIdLeitor(String idLeitor) {
        ArrayList<Emprestimo> emprestimoArrayList = new ArrayList<Emprestimo>();
        for (Emprestimo emprestimo : this.emprestimoList) {
            if (emprestimo.getIdLeitor().equals(idLeitor)) {
                emprestimoArrayList.add(emprestimo);
            }
        }
        return emprestimoArrayList;
    }

    @Override
    public List<Emprestimo> findAll() {
        return this.emprestimoList;
    }
}