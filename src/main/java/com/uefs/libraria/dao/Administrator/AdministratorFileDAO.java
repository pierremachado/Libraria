package main.java.com.uefs.libraria.dao.Administrator;

import main.java.com.uefs.libraria.model.Administrator;
import main.java.com.uefs.libraria.util.FileStorage;

import java.util.List;

public class AdministratorFileDAO implements AdministratorDAO {
    private final List<Administrator> administratorList;
    private final FileStorage fs;

    public AdministratorFileDAO() {
        this.fs = new FileStorage("administrator", "administrator");
        this.administratorList = fs.ler();
    }

    @Override
    public Administrator update(Administrator obj) {
        int index = this.administratorList.indexOf(obj);
        this.administratorList.set(index, obj);
        this.fs.salvar(administratorList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrator adm : this.administratorList) {
            if (adm.getId().equals(id)) {
                this.administratorList.remove(adm);
                this.fs.salvar(administratorList);
                return;
            }
        }
    }

    @Override
    public Administrator create(Administrator obj) {
        this.administratorList.add(obj);
        this.fs.salvar(administratorList);
        return obj;
    }

    @Override
    public Administrator findID(String id) {
        for (Administrator adm : this.administratorList) {
            if (adm.getId().equals(id))
                return adm;
        }

        return null;
    }

    @Override
    public List<Administrator> findAll() {
        return this.administratorList;
    }
}
