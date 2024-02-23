package com.uefs.libraria.dao.Loan;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.User;
import com.uefs.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uefs.libraria.dao.DAO.getEmprestimoDAO;
import static java.lang.String.valueOf;

public class LoanFileDAO implements LoanDAO {
    private final List<Loan> loanList;
    private final FileStorage fs;
    private int nextId = 1;

    public LoanFileDAO() {
        this.fs = new FileStorage("loan", "loan");
        this.loanList = fs.read();
        this.nextId = calculateNextId();
    }

    private int calculateNextId() {
        int maxId = 0;
        for (Loan loan : loanList) {
            int id = Integer.parseInt(loan.getIdEmprestimo());
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    @Override
    public Loan update(Loan obj) {
        int index = this.loanList.indexOf(obj);
        this.loanList.set(index, obj);
        fs.save(loanList);
        return obj;
    }

    @Override
    public void updateLoanId(User userToEdit, String newId){
        for(Loan loan : getEmprestimoDAO().findAll()){
            if(Objects.equals(loan.getIdLeitor(), userToEdit.getId())){
                loan.setIdLeitor(newId);
            }
            if(Objects.equals(loan.getIdOperador(), userToEdit.getId())){
                loan.setIdOperador(newId);
            }
        }
        fs.save(loanList);
    }

    @Override
    public void updateLoanIsbn(Book bookToEdit, String newIsbn){
        for(Loan loan : getEmprestimoDAO().findAll()){
            if(Objects.equals(loan.getIdLivro(), bookToEdit.getIsbn())){
                loan.setIdLivro(newIsbn);
            }
            fs.save(loanList);
        }
    }

    @Override
    public void deleteID(String id) {
        for (Loan loan : this.loanList) {
            if (loan.getIdEmprestimo().equals(id)) {
                this.loanList.remove(loan);
                fs.save(loanList);
                return;
            }
        }
    }

    @Override
    public Loan create(Loan obj) {
        obj.setIdEmprestimo(valueOf(nextId++));
        this.loanList.add(obj);
        fs.save(loanList);
        return obj;
    }

    @Override
    public Loan findID(String id) {
        for (Loan loan : this.loanList) {
            if (loan.getIdEmprestimo().equals(id))
                return loan;
        }

        return null;
    }

    @Override
    public List<Loan> findIdLivro(String idLivro) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.loanList) {
            if (loan.getIdLivro().equals(idLivro)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findIdOperador(String idOperador) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.loanList) {
            if (loan.getIdOperador().equals(idOperador)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findIdLeitor(String idLeitor) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.loanList) {
            if (loan.getIdLeitor().equals(idLeitor)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findAll() {
        return this.loanList;
    }
}