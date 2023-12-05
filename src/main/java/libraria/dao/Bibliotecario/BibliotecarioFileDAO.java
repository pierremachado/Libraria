package main.java.libraria.dao.Bibliotecario;

import main.java.libraria.dao.Bibliotecario.BibliotecarioDAO;
import main.java.libraria.model.Bibliotecario;
import main.java.libraria.util.FileStorage;

import java.util.List;

public class BibliotecarioFileDAO implements BibliotecarioDAO {
    private final List<Bibliotecario> bibliotecarioList;
    private final FileStorage fs;

    public BibliotecarioFileDAO() {
        this.fs = new FileStorage("Bibliotecario", "Bibliotecario");
        this.bibliotecarioList = fs.ler();
    }

    @Override
    public Bibliotecario update(Bibliotecario obj) {
        int index = this.bibliotecarioList.indexOf(obj);
        this.bibliotecarioList.set(index, obj);
        this.fs.salvar(bibliotecarioList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Bibliotecario bib : this.bibliotecarioList) {
            if (bib.getId().equals(id)) {
                this.bibliotecarioList.remove(bib);
                this.fs.salvar(bibliotecarioList);
                return;
            }
        }
    }

    @Override
    public Bibliotecario create(Bibliotecario obj) {
        this.bibliotecarioList.add(obj);
        this.fs.salvar(bibliotecarioList);
        return obj;
    }

    @Override
    public Bibliotecario findID(String id) {
        for (Bibliotecario bib : this.bibliotecarioList) {
            if (bib.getId().equals(id))
                return bib;
        }

        return null;
    }

    @Override
    public List<Bibliotecario> findAll() {
        return this.bibliotecarioList;
    }
}