package main.java.com.uefs.libraria.dao.Reader;

import main.java.com.uefs.libraria.model.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class ReaderListDAO implements ReaderDAO {

    private final List<Reader> lista;

    public ReaderListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Reader update(Reader obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reader reader : this.lista) {
            if (reader.getId().equals(id)) {
                this.lista.remove(reader);
                return;
            }
        }
    }

    @Override
    public Reader create(Reader obj) {
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Reader findID(String id) {
        for (Reader reader : this.lista) {
            if (reader.getId().equals(id))
                return reader;
        }

        return null;
    }

    @Override
    public List<Reader> findAll() {
        return this.lista;
    }
}