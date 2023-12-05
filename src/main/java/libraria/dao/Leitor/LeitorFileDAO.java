package main.java.libraria.dao.Leitor;

import main.java.libraria.model.Leitor;
import main.java.libraria.util.FileStorage;

import java.util.List;

public class LeitorFileDAO implements LeitorDAO {
    private final List<Leitor> leitorList;
    private final FileStorage fs;

    public LeitorFileDAO() {
        this.fs = new FileStorage("Leitor", "Leitor");
        this.leitorList = fs.ler();
    }

    @Override
    public Leitor update(Leitor obj) {
        int index = this.leitorList.indexOf(obj);
        this.leitorList.set(index, obj);
        this.fs.salvar(leitorList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Leitor leitor : this.leitorList) {
            if (leitor.getId().equals(id)) {
                this.leitorList.remove(leitor);
                this.fs.salvar(leitorList);
                return;
            }
        }
    }

    @Override
    public Leitor create(Leitor obj) {
        this.leitorList.add(obj);
        this.fs.salvar(leitorList);
        return obj;
    }

    @Override
    public Leitor findID(String id) {
        for (Leitor leitor : this.leitorList) {
            if (leitor.getId().equals(id))
                return leitor;
        }

        return null;
    }

    @Override
    public List<Leitor> findAll() {
        return this.leitorList;
    }
}