package com.uefs.libraria.dao.Reader;

import com.uefs.libraria.model.Reader;
import com.uefs.libraria.util.FileStorage;

import java.util.List;

public class ReaderFileDAO implements ReaderDAO {
    private final List<Reader> readerList;
    private final FileStorage fs;

    public ReaderFileDAO() {
        this.fs = new FileStorage("reader", "reader");
        this.readerList = fs.read();
    }

    @Override
    public Reader update(Reader obj) {
        int index = this.readerList.indexOf(obj);
        this.readerList.set(index, obj);
        this.fs.save(readerList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Reader reader : this.readerList) {
            if (reader.getId().equals(id)) {
                this.readerList.remove(reader);
                this.fs.save(readerList);
                return;
            }
        }
    }

    @Override
    public Reader create(Reader obj) {
        this.readerList.add(obj);
        this.fs.save(readerList);
        return obj;
    }

    @Override
    public Reader findID(String id) {
        for (Reader reader : this.readerList) {
            if (reader.getId().equals(id))
                return reader;
        }

        return null;
    }

    @Override
    public List<Reader> findAll() {
        return this.readerList;
    }
}