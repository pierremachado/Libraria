package com.uefs.libraria.dao.Librarian;

import com.uefs.libraria.model.Librarian;
import com.uefs.libraria.util.FileStorage;

import java.util.List;

public class LibrarianFileDAO implements LibrarianDAO {
    private final List<Librarian> librarianList;
    private final FileStorage fs;

    public LibrarianFileDAO() {
        this.fs = new FileStorage("librarian", "librarian");
        this.librarianList = fs.read();
    }

    @Override
    public Librarian update(Librarian obj) {
        int index = this.librarianList.indexOf(obj);
        this.librarianList.set(index, obj);
        this.fs.save(librarianList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Librarian bib : this.librarianList) {
            if (bib.getId().equals(id)) {
                this.librarianList.remove(bib);
                this.fs.save(librarianList);
                return;
            }
        }
    }

    @Override
    public Librarian create(Librarian obj) {
        this.librarianList.add(obj);
        this.fs.save(librarianList);
        return obj;
    }

    @Override
    public Librarian findID(String id) {
        for (Librarian bib : this.librarianList) {
            if (bib.getId().equals(id))
                return bib;
        }

        return null;
    }

    @Override
    public List<Librarian> findAll() {
        return this.librarianList;
    }
}