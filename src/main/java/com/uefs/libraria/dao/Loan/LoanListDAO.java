package com.uefs.libraria.dao.Loan;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uefs.libraria.dao.DAO.getEmprestimoDAO;
import static java.lang.String.valueOf;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class LoanListDAO implements LoanDAO {

    private final List<Loan> lista;
    private int nextId = 1;

    public LoanListDAO() {
        this.lista = new ArrayList<>();
    }

    @Override
    public Loan update(Loan obj) {
        int index = this.lista.indexOf(obj);
        this.lista.set(index, obj);
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
    }

    @Override
    public void updateLoanIsbn(Book bookToEdit, String newIsbn){
        for(Loan loan : getEmprestimoDAO().findAll()){
            if(Objects.equals(loan.getIdLivro(), bookToEdit.getIsbn())){
                loan.setIdLivro(newIsbn);
            }
        }
    }

    @Override
    public void deleteID(String id) {
        for (Loan loan : this.lista) {
            if (loan.getIdEmprestimo().equals(id)) {
                this.lista.remove(loan);
                return;
            }
        }
    }

    @Override
    public Loan create(Loan obj) {
        obj.setIdEmprestimo(valueOf(nextId++));
        this.lista.add(obj);
        return obj;
    }

    @Override
    public Loan findID(String id) {
        for (Loan loan : this.lista) {
            if (loan.getIdEmprestimo().equals(id))
                return loan;
        }

        return null;
    }

    @Override
    public List<Loan> findIdLivro(String idLivro) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.lista) {
            if (loan.getIdLivro().equals(idLivro)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findIdOperador(String idOperador) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.lista) {
            if (loan.getIdOperador().equals(idOperador)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findIdLeitor(String idLeitor) {
        ArrayList<Loan> loanArrayList = new ArrayList<Loan>();
        for (Loan loan : this.lista) {
            if (loan.getIdLeitor().equals(idLeitor)) {
                loanArrayList.add(loan);
            }
        }
        return loanArrayList;
    }

    @Override
    public List<Loan> findAll() {
        return this.lista;
    }
}