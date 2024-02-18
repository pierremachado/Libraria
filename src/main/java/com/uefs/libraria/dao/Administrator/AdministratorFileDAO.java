package com.uefs.libraria.dao.Administrator;

import com.uefs.libraria.model.Administrator;
import com.uefs.libraria.util.FileStorage;

import java.util.List;

public class AdministratorFileDAO implements AdministratorDAO {
    private final List<Administrator> administratorList;
    private final FileStorage fs;

    public AdministratorFileDAO() {
        this.fs = new FileStorage("administrator", "administrator");
        this.administratorList = fs.read();
    }

    @Override
    public Administrator update(Administrator obj) {
        int index = this.administratorList.indexOf(obj);
        this.administratorList.set(index, obj);
        this.fs.save(administratorList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrator adm : this.administratorList) {
            if (adm.getId().equals(id)) {
                this.administratorList.remove(adm);
                this.fs.save(administratorList);
                return;
            }
        }
    }

    @Override
    public Administrator create(Administrator obj) {
        this.administratorList.add(obj);
        this.fs.save(administratorList);
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
