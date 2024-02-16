package com.uefs.libraria.dao.Loan;

import com.uefs.libraria.dao.CRUD;
import com.uefs.libraria.model.Loan;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public interface LoanDAO extends CRUD<Loan> {
    List<Loan> findIdLivro(String idLivro);

    List<Loan> findIdOperador(String idOperador);

    List<Loan> findIdLeitor(String idLeitor);
}
