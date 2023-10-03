package main.java.libraria.dao.Administrador;

import main.java.libraria.model.Administrador;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class AdministradorListDAO implements AdministradorDAO {

    private final List<Administrador> administradorList;

    public AdministradorListDAO() {
        this.administradorList = new ArrayList<>();
    }

    @Override
    public Administrador update(Administrador obj) {
        int index = this.administradorList.indexOf(obj);
        this.administradorList.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrador adm : this.administradorList) {
            if (adm.getId().equals(id)) {
                this.administradorList.remove(adm);
                return;
            }
        }
    }

    @Override
    public Administrador create(Administrador obj) {
        this.administradorList.add(obj);
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
