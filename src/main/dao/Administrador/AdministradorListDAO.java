package main.dao.Administrador;

import main.model.Administrador;

import java.util.ArrayList;
import java.util.List;

public class AdministradorListDAO implements AdministradorDAO{

    private List<Administrador> lista;

    public AdministradorListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Administrador update(Administrador obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Administrador adm : this.lista){
            if(adm.getId().equals(id)) {
                this.lista.remove(adm);
                return;
            }
        }
    }

    @Override
    public Administrador create(Administrador obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Administrador findID(String id) {
        for(Administrador adm : this.lista){
            if(adm.getId().equals(id))
                return adm;
        }

        return null;
    }

    @Override
    public List<Administrador> findAll() {
        return this.lista;
    }
}
