package dao.Administrador;

import model.Administrador;

import java.util.ArrayList;
import java.util.List;

public class AdministradorList implements AdministradorDAO{

    private List<Administrador> lista;

    public AdministradorList() {
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
    public List<Administrador> findall() {
        return this.lista;
    }
}
