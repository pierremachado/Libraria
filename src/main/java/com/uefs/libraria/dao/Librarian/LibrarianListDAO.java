package com.uefs.libraria.dao.Librarian;

import com.uefs.libraria.model.Librarian;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class LibrarianListDAO implements LibrarianDAO {

    private final List<Librarian> lista;

    public LibrarianListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Librarian update(Librarian obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Librarian bib : this.lista) {
            if (bib.getId().equals(id)) {
                this.lista.remove(bib);
                return;
            }
        }
    }

    @Override
    public Librarian create(Librarian obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Librarian findID(String id) {
        for (Librarian bib : this.lista) {
            if (bib.getId().equals(id))
                return bib;
        }

        return null;
    }

    @Override
    public List<Librarian> findAll() {
        return this.lista;
    }
}
