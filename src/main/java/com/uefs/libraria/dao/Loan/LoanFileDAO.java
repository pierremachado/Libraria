package main.java.com.uefs.libraria.dao.Loan;

import main.java.com.uefs.libraria.model.Loan;
import main.java.com.uefs.libraria.util.FileStorage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class LoanFileDAO implements LoanDAO {
    private final List<Loan> loanList;
    private final FileStorage fs;
    private int nextId = 1;

    public LoanFileDAO() {
        this.fs = new FileStorage("loan", "loan");
        this.loanList = fs.ler();
    }

    @Override
    public Loan update(Loan obj) {
        int index = this.loanList.indexOf(obj);
        this.loanList.set(index, obj);
        fs.salvar(loanList);
        return obj;
    }

    @Override
    public void deleteID(String id) {
        for (Loan loan : this.loanList) {
            if (loan.getIdEmprestimo().equals(id)) {
                this.loanList.remove(loan);
                fs.salvar(loanList);
                return;
            }
        }
    }

    @Override
    public Loan create(Loan obj) {
        obj.setIdEmprestimo(valueOf(nextId++));
        this.loanList.add(obj);
        fs.salvar(loanList);
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