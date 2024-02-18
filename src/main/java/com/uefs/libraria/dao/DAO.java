package com.uefs.libraria.dao;

import com.uefs.libraria.dao.Administrator.AdministratorDAO;
import com.uefs.libraria.dao.Administrator.AdministratorFileDAO;
import com.uefs.libraria.dao.Administrator.AdministratorListDAO;
import com.uefs.libraria.dao.Librarian.LibrarianDAO;
import com.uefs.libraria.dao.Librarian.LibrarianFileDAO;
import com.uefs.libraria.dao.Librarian.LibrarianListDAO;
import com.uefs.libraria.dao.Loan.LoanDAO;
import com.uefs.libraria.dao.Loan.LoanFileDAO;
import com.uefs.libraria.dao.Loan.LoanListDAO;
import com.uefs.libraria.dao.Reader.ReaderDAO;
import com.uefs.libraria.dao.Reader.ReaderFileDAO;
import com.uefs.libraria.dao.Reader.ReaderListDAO;
import com.uefs.libraria.dao.Book.BookDAO;
import com.uefs.libraria.dao.Book.BookFileDAO;
import com.uefs.libraria.dao.Book.BookListDAO;
import com.uefs.libraria.dao.Reservation.ReservationDAO;
import com.uefs.libraria.dao.Reservation.ReservationFileDAO;
import com.uefs.libraria.dao.Reservation.ReservationListDAO;
import com.uefs.libraria.model.enums.DAOStatus;
import com.uefs.libraria.util.FileStorage;

import java.io.File;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class DAO {

    private static AdministratorDAO administratorDAO;
    private static LibrarianDAO librarianDAO;
    private static LoanDAO loanDAO;
    private static ReaderDAO readerDAO;
    private static BookDAO bookDAO;
    private static ReservationDAO reservationDAO;
    private static final DAOStatus statusDAO = DAOStatus.FILE;

    public static AdministratorDAO getAdministradorDAO() {
        if (administratorDAO == null) {
            switch (statusDAO) {
                case MEMORY -> administratorDAO = new AdministratorListDAO();
                case FILE -> administratorDAO = new AdministratorFileDAO();
            }
        }
        return administratorDAO;
    }

    public static LibrarianDAO getBibliotecarioDAO() {
        if (librarianDAO == null) {
            switch (statusDAO) {
                case MEMORY -> librarianDAO = new LibrarianListDAO();
                case FILE -> librarianDAO = new LibrarianFileDAO();
            }
        }
        return librarianDAO;
    }

    public static LoanDAO getEmprestimoDAO() {
        if (loanDAO == null) {
            switch (statusDAO) {
                case MEMORY -> loanDAO = new LoanListDAO();
                case FILE -> loanDAO = new LoanFileDAO();
            }
        }
        return loanDAO;
    }

    public static ReaderDAO getLeitorDAO() {
        if (readerDAO == null) {
            switch (statusDAO) {
                case MEMORY -> readerDAO = new ReaderListDAO();
                case FILE -> readerDAO = new ReaderFileDAO();
            }
        }
        return readerDAO;
    }

    public static BookDAO getLivroDAO() {
        if (bookDAO == null) {
            switch (statusDAO) {
                case MEMORY -> bookDAO = new BookListDAO();
                case FILE -> bookDAO = new BookFileDAO();
            }
        }
        return bookDAO;
    }

    /**
     * @return Instância do DAO.
     */

    public static ReservationDAO getReservaDAO() {
        if (reservationDAO == null) {
            switch (statusDAO) {
                case MEMORY -> reservationDAO = new ReservationListDAO();
                case FILE -> reservationDAO = new ReservationFileDAO();
            }
        }
        return reservationDAO;
    }

    /**
     * Função que deleta todos os arquivos de um diretório para fins de teste.
     * Trecho de código adaptado de Jeff Learman encontrado em <a href="https://stackoverflow.com/questions/20281835/how-to-delete-a-folder-with-files-using-java">...</a>
     * Fonte: <a href="https://stackoverflow.com/questions/20281835/how-to-delete-a-folder-with-files-using-java">...</a>
     *
     * @author Jeff Learman
     */
    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }


    public static void limparDadosDAO() {
        administratorDAO = null;
        librarianDAO = null;
        loanDAO = null;
        readerDAO = null;
        bookDAO = null;
        reservationDAO = null;

        if (statusDAO.equals(DAOStatus.FILE)) { // para fins de teste
            File pasta = new File(FileStorage.getMainDir());
            deleteDir(pasta);
        }
    }
}