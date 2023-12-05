package main.java.libraria.dao.Administrador;

import main.java.libraria.model.Administrador;
import main.java.libraria.util.FileStorage;

import java.util.List;

public class AdministradorFileDAO implements AdministradorDAO {
    private final List<Administrador> administradorList;
    private final FileStorage fs;

    public AdministradorFileDAO() {
        this.fs = new FileStorage("Administrador", "Administrador");
        this.administradorList = fs.ler();
    }

    @Override
    public Administrador update(Administrador obj) {
        int index = this.administradorList.indexOf(obj);
        this.administradorList.set(index, obj);
        this.fs.salvar(administradorList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrador adm : this.administradorList) {
            if (adm.getId().equals(id)) {
                this.administradorList.remove(adm);
                this.fs.salvar(administradorList);
                return;
            }
        }
    }

    @Override
    public Administrador create(Administrador obj) {
        this.administradorList.add(obj);
        this.fs.salvar(administradorList);
        return obj;
    }

    @Override
    public Administrador findID(String id) {
        for (Administrador adm : this.administradorList) {
            if (adm.getId().equals(id))
                return adm;
        }

        return null;
    }

    @Override
    public List<Administrador> findAll() {
        return this.administradorList;
    }
}
