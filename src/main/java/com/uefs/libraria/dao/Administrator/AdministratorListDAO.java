package main.java.com.uefs.libraria.dao.Administrator;

import main.java.com.uefs.libraria.model.Administrator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class AdministratorListDAO implements AdministratorDAO {

    private final List<Administrator> administratorList;

    public AdministratorListDAO() {
        this.administratorList = new ArrayList<>();
    }

    @Override
    public Administrator update(Administrator obj) {
        int index = this.administratorList.indexOf(obj);
        this.administratorList.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrator adm : this.administratorList) {
            if (adm.getId().equals(id)) {
                this.administratorList.remove(adm);
                return;
            }
        }
    }

    @Override
    public Administrator create(Administrator obj) {
        this.administratorList.add(obj);
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
