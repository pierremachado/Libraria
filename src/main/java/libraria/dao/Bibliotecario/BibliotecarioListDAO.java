package main.java.libraria.dao.Bibliotecario;

import main.java.libraria.model.Bibliotecario;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class BibliotecarioListDAO implements BibliotecarioDAO {

    private final List<Bibliotecario> lista;

    public BibliotecarioListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Bibliotecario update(Bibliotecario obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Bibliotecario bib : this.lista) {
            if (bib.getId().equals(id)) {
                this.lista.remove(bib);
                return;
            }
        }
    }

    @Override
    public Bibliotecario create(Bibliotecario obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Bibliotecario findID(String id) {
        for (Bibliotecario bib : this.lista) {
            if (bib.getId().equals(id))
                return bib;
        }

        return null;
    }

    @Override
    public List<Bibliotecario> findAll() {
        return this.lista;
    }
}
