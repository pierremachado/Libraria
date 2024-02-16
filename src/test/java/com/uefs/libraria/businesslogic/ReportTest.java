package com.uefs.libraria.businesslogic;

import com.uefs.libraria.model.*;
import com.uefs.libraria.model.enums.*;
import com.uefs.libraria.dao.*;
import com.uefs.libraria.services.*;
import com.uefs.libraria.exceptions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class ReportTest {
    private Administrator adm;
    private Librarian bib;
    private Reader reader;
    private Book book;
    private Loan loan;
    @BeforeEach
    public void setUp(){
        adm = new Administrator("admin", "", "admin", "admin");
        bib = new Librarian("sérgio", "sérgio", "sergiosergio", "senha");
        reader = new Reader("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        book = new Book("Você é lindo", "Fulano de Tal", "Editora", "123456", Year.of(2013), "Auto-ajuda", 5, 0);
        loan = new Loan(adm.getId(), adm.getPermissao(), reader.getId(), book.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, 0, LoanStatus.PENDENTE);

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(reader);
        DAO.getLivroDAO().create(book);
        DAO.getEmprestimoDAO().create(loan);
    }
    @AfterEach
    public void tearDown(){
        LoginService.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarRelatorio(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());

        Report report = null;
        try {
            report = ReportService.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(report);
        assertEquals(report.getnLivrosReservados(), 0);
        assertEquals(report.getnLivrosEmprestados(), 1);
        assertEquals(report.getnLivrosAtrasados(), 0);
        assertEquals(report.getIdAdministrador(), adm.getId());
        assertEquals(report.getLivrosPopulares().get(0), book);

        TimeService.setCurrentLocalDateTime(LocalDateTime.now().plusDays(10));

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        report = null;
        try {
            report = ReportService.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(report);
        assertEquals(report.getnLivrosReservados(), 0);
        assertEquals(report.getnLivrosEmprestados(), 1);
        assertEquals(report.getnLivrosAtrasados(), 1);
    }
}
