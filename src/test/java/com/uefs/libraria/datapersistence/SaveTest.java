package test.java.com.uefs.libraria.datapersistence;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.model.*;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;
import main.java.com.uefs.libraria.model.enums.UserPermission;

import java.time.LocalDateTime;
import java.time.Year;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class SaveTest {
    public static void main(String[] args) {
        Administrator admin1 = new Administrator("João", "Silva", "001", "senha123");
        Administrator admin2 = new Administrator("Maria", "Santos", "002", "adminPass");
        Administrator admin3 = new Administrator("Pedro", "Souza", "003", "securePwd");
        Administrator admin4 = new Administrator("Ana", "Carvalho", "004", "ana123");
        Administrator admin5 = new Administrator("Carlos", "Oliveira", "005", "carlosPass");
        Administrator admin6 = new Administrator("Mariana", "Costa", "006", "marianaPwd");
        Administrator admin7 = new Administrator("Rafael", "Pereira", "007", "rafaelPass");
        Administrator admin8 = new Administrator("Fernanda", "Rodrigues", "008", "fer123");
        Administrator admin9 = new Administrator("Lucas", "Ferreira", "009", "lucasPass");
        Administrator admin10 = new Administrator("Larissa", "Almeida", "010", "larissaPwd");

        DAO.getAdministradorDAO().create(admin1);
        DAO.getAdministradorDAO().create(admin2);
        DAO.getAdministradorDAO().create(admin3);
        DAO.getAdministradorDAO().create(admin4);
        DAO.getAdministradorDAO().create(admin5);
        DAO.getAdministradorDAO().create(admin6);
        DAO.getAdministradorDAO().create(admin7);
        DAO.getAdministradorDAO().create(admin8);
        DAO.getAdministradorDAO().create(admin9);
        DAO.getAdministradorDAO().create(admin10);

        Librarian bib1 = new Librarian("Carlos", "Silva", "bib001", "senha123");
        Librarian bib2 = new Librarian("Ana", "Santos", "bib002", "adminPass");
        Librarian bib3 = new Librarian("Mariana", "Oliveira", "bib003", "securePwd");
        Librarian bib4 = new Librarian("Pedro", "Costa", "bib004", "biblio123");
        Librarian bib5 = new Librarian("Rafael", "Pereira", "bib005", "biblioPass");
        Librarian bib6 = new Librarian("Fernanda", "Rodrigues", "bib006", "fer321");
        Librarian bib7 = new Librarian("Lucas", "Ferreira", "bib007", "lucasPass");
        Librarian bib8 = new Librarian("Larissa", "Almeida", "bib008", "larissaPwd");
        Librarian bib9 = new Librarian("João", "Carvalho", "bib009", "joao123");
        Librarian bib10 = new Librarian("Maria", "Mendes", "bib010", "mariaPass");

        DAO.getBibliotecarioDAO().create(bib1);
        DAO.getBibliotecarioDAO().create(bib2);
        DAO.getBibliotecarioDAO().create(bib3);
        DAO.getBibliotecarioDAO().create(bib4);
        DAO.getBibliotecarioDAO().create(bib5);
        DAO.getBibliotecarioDAO().create(bib6);
        DAO.getBibliotecarioDAO().create(bib7);
        DAO.getBibliotecarioDAO().create(bib8);
        DAO.getBibliotecarioDAO().create(bib9);
        DAO.getBibliotecarioDAO().create(bib10);

        Reader reader1 = new Reader("Ana", "Silva", "leitor001", "senha123", "Rua A, 123", "(11) 98765-4321");
        Reader reader2 = new Reader("Carlos", "Santos", "leitor002", "leitorPass", "Av. B, 456", "(22) 12345-6789");
        Reader reader3 = new Reader("Mariana", "Oliveira", "leitor003", "marianaPwd", "Rua C, 789", "(33) 54321-9876");
        Reader reader4 = new Reader("Pedro", "Costa", "leitor004", "senha456", "Av. D, 101", "(44) 13579-2468");
        Reader reader5 = new Reader("Rafael", "Pereira", "leitor005", "rafael123", "Rua E, 202", "(55) 98765-4321");
        Reader reader6 = new Reader("Fernanda", "Rodrigues", "leitor006", "fer456", "Av. F, 303", "(66) 12345-6789");
        Reader reader7 = new Reader("Lucas", "Ferreira", "leitor007", "lucasPwd", "Rua G, 404", "(77) 54321-9876");
        Reader reader8 = new Reader("Larissa", "Almeida", "leitor008", "larissaPass", "Av. H, 505", "(88) 13579-2468");
        Reader reader9 = new Reader("João", "Carvalho", "leitor009", "joao789", "Rua I, 606", "(99) 98765-4321");
        Reader reader10 = new Reader("Maria", "Mendes", "leitor010", "maria123", "Av. J, 707", "(00) 12345-6789");

        DAO.getLeitorDAO().create(reader1);
        DAO.getLeitorDAO().create(reader2);
        DAO.getLeitorDAO().create(reader3);
        DAO.getLeitorDAO().create(reader4);
        DAO.getLeitorDAO().create(reader5);
        DAO.getLeitorDAO().create(reader6);
        DAO.getLeitorDAO().create(reader7);
        DAO.getLeitorDAO().create(reader8);
        DAO.getLeitorDAO().create(reader9);
        DAO.getLeitorDAO().create(reader10);

        Book book1 = new Book("Aprendendo Java", "João Silva", "Editora A", "978-3-16-148410-0", Year.of(2020), "Programação", 10, 5);
        Book book2 = new Book("Algoritmos e Estruturas de Dados", "Maria Santos", "Editora B", "978-3-16-148410-1", Year.of(2019), "Ciência da Computação", 8, 7);
        Book book3 = new Book("Introdução à Inteligência Artificial", "Carlos Oliveira", "Editora C", "978-3-16-148410-2", Year.of(2021), "Inteligência Artificial", 15, 3);
        Book book4 = new Book("Banco de Dados Avançado", "Ana Costa", "Editora D", "978-3-16-148410-3", Year.of(2018), "Banco de Dados", 12, 9);
        Book book5 = new Book("Redes de Computadores", "Pedro Souza", "Editora E", "978-3-16-148410-4", Year.of(2017), "Redes", 20, 12);
        Book book6 = new Book("Sistemas Operacionais Modernos", "Mariana Pereira", "Editora F", "978-3-16-148410-5", Year.of(2016), "Sistemas Operacionais", 18, 8);
        Book book7 = new Book("Desenvolvimento Web", "Rafael Rodrigues", "Editora G", "978-3-16-148410-6", Year.of(2022), "Desenvolvimento Web", 25, 10);
        Book book8 = new Book("Machine Learning para Iniciantes", "Fernanda Almeida", "Editora H", "978-3-16-148410-7", Year.of(2020), "Machine Learning", 30, 6);
        Book book9 = new Book("Matemática para Engenharia", "Lucas Ferreira", "Editora I", "978-3-16-148410-8", Year.of(2019), "Matemática", 14, 4);
        Book book10 = new Book("Física Quântica", "Larissa Almeida", "Editora J", "978-3-16-148410-9", Year.of(2021), "Física", 22, 11);

        DAO.getLivroDAO().create(book1);
        DAO.getLivroDAO().create(book2);
        DAO.getLivroDAO().create(book3);
        DAO.getLivroDAO().create(book4);
        DAO.getLivroDAO().create(book5);
        DAO.getLivroDAO().create(book6);
        DAO.getLivroDAO().create(book7);
        DAO.getLivroDAO().create(book8);
        DAO.getLivroDAO().create(book9);
        DAO.getLivroDAO().create(book10);

        Reservation reservation1 = new Reservation("reserva001", reader1.getId(), book1.getIsbn(), ReservationStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reservation reservation2 = new Reservation("reserva002", reader2.getId(), book2.getIsbn(), ReservationStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reservation reservation3 = new Reservation("reserva003", reader3.getId(), book3.getIsbn(), ReservationStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reservation reservation4 = new Reservation("reserva004", reader4.getId(), book4.getIsbn(), ReservationStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reservation reservation5 = new Reservation("reserva005", reader5.getId(), book5.getIsbn(), ReservationStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        DAO.getReservaDAO().create(reservation1);
        DAO.getReservaDAO().create(reservation2);
        DAO.getReservaDAO().create(reservation3);
        DAO.getReservaDAO().create(reservation4);
        DAO.getReservaDAO().create(reservation5);

        Loan loan1 = new Loan("admin1", UserPermission.ADMINISTRADOR, reader6.getId(), book6.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, LoanStatus.PENDENTE);
        Loan loan2 = new Loan("admin2", UserPermission.ADMINISTRADOR, reader7.getId(), book7.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, LoanStatus.PENDENTE);
        Loan loan3 = new Loan("bibliotecario1", UserPermission.BIBLIOTECARIO, reader8.getId(), book8.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, LoanStatus.PENDENTE);
        Loan loan4 = new Loan("bibliotecario2", UserPermission.BIBLIOTECARIO, reader9.getId(), book9.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, LoanStatus.PENDENTE);
        Loan loan5 = new Loan("admin3", UserPermission.ADMINISTRADOR, reader10.getId(), book10.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, LoanStatus.PENDENTE);

        DAO.getEmprestimoDAO().create(loan1);
        DAO.getEmprestimoDAO().create(loan2);
        DAO.getEmprestimoDAO().create(loan3);
        DAO.getEmprestimoDAO().create(loan4);
        DAO.getEmprestimoDAO().create(loan5);
    }
}