package test.datapersistence;

import main.java.libraria.dao.DAO;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.ReservaStatus;
import main.java.libraria.model.enums.UserPermissao;

import java.time.LocalDateTime;
import java.time.Year;

public class Save {
    public static void main(String[] args) {
        Administrador admin1 = new Administrador("João", "Silva", "001", "senha123");
        Administrador admin2 = new Administrador("Maria", "Santos", "002", "adminPass");
        Administrador admin3 = new Administrador("Pedro", "Souza", "003", "securePwd");
        Administrador admin4 = new Administrador("Ana", "Carvalho", "004", "ana123");
        Administrador admin5 = new Administrador("Carlos", "Oliveira", "005", "carlosPass");
        Administrador admin6 = new Administrador("Mariana", "Costa", "006", "marianaPwd");
        Administrador admin7 = new Administrador("Rafael", "Pereira", "007", "rafaelPass");
        Administrador admin8 = new Administrador("Fernanda", "Rodrigues", "008", "fer123");
        Administrador admin9 = new Administrador("Lucas", "Ferreira", "009", "lucasPass");
        Administrador admin10 = new Administrador("Larissa", "Almeida", "010", "larissaPwd");

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

        Bibliotecario bib1 = new Bibliotecario("Carlos", "Silva", "bib001", "senha123");
        Bibliotecario bib2 = new Bibliotecario("Ana", "Santos", "bib002", "adminPass");
        Bibliotecario bib3 = new Bibliotecario("Mariana", "Oliveira", "bib003", "securePwd");
        Bibliotecario bib4 = new Bibliotecario("Pedro", "Costa", "bib004", "biblio123");
        Bibliotecario bib5 = new Bibliotecario("Rafael", "Pereira", "bib005", "biblioPass");
        Bibliotecario bib6 = new Bibliotecario("Fernanda", "Rodrigues", "bib006", "fer321");
        Bibliotecario bib7 = new Bibliotecario("Lucas", "Ferreira", "bib007", "lucasPass");
        Bibliotecario bib8 = new Bibliotecario("Larissa", "Almeida", "bib008", "larissaPwd");
        Bibliotecario bib9 = new Bibliotecario("João", "Carvalho", "bib009", "joao123");
        Bibliotecario bib10 = new Bibliotecario("Maria", "Mendes", "bib010", "mariaPass");

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

        Leitor leitor1 = new Leitor("Ana", "Silva", "leitor001", "senha123", "Rua A, 123", "(11) 98765-4321");
        Leitor leitor2 = new Leitor("Carlos", "Santos", "leitor002", "leitorPass", "Av. B, 456", "(22) 12345-6789");
        Leitor leitor3 = new Leitor("Mariana", "Oliveira", "leitor003", "marianaPwd", "Rua C, 789", "(33) 54321-9876");
        Leitor leitor4 = new Leitor("Pedro", "Costa", "leitor004", "senha456", "Av. D, 101", "(44) 13579-2468");
        Leitor leitor5 = new Leitor("Rafael", "Pereira", "leitor005", "rafael123", "Rua E, 202", "(55) 98765-4321");
        Leitor leitor6 = new Leitor("Fernanda", "Rodrigues", "leitor006", "fer456", "Av. F, 303", "(66) 12345-6789");
        Leitor leitor7 = new Leitor("Lucas", "Ferreira", "leitor007", "lucasPwd", "Rua G, 404", "(77) 54321-9876");
        Leitor leitor8 = new Leitor("Larissa", "Almeida", "leitor008", "larissaPass", "Av. H, 505", "(88) 13579-2468");
        Leitor leitor9 = new Leitor("João", "Carvalho", "leitor009", "joao789", "Rua I, 606", "(99) 98765-4321");
        Leitor leitor10 = new Leitor("Maria", "Mendes", "leitor010", "maria123", "Av. J, 707", "(00) 12345-6789");

        DAO.getLeitorDAO().create(leitor1);
        DAO.getLeitorDAO().create(leitor2);
        DAO.getLeitorDAO().create(leitor3);
        DAO.getLeitorDAO().create(leitor4);
        DAO.getLeitorDAO().create(leitor5);
        DAO.getLeitorDAO().create(leitor6);
        DAO.getLeitorDAO().create(leitor7);
        DAO.getLeitorDAO().create(leitor8);
        DAO.getLeitorDAO().create(leitor9);
        DAO.getLeitorDAO().create(leitor10);

        Livro livro1 = new Livro("Aprendendo Java", "João Silva", "Editora A", "978-3-16-148410-0", Year.of(2020), "Programação", 10, 5);
        Livro livro2 = new Livro("Algoritmos e Estruturas de Dados", "Maria Santos", "Editora B", "978-3-16-148410-1", Year.of(2019), "Ciência da Computação", 8, 7);
        Livro livro3 = new Livro("Introdução à Inteligência Artificial", "Carlos Oliveira", "Editora C", "978-3-16-148410-2", Year.of(2021), "Inteligência Artificial", 15, 3);
        Livro livro4 = new Livro("Banco de Dados Avançado", "Ana Costa", "Editora D", "978-3-16-148410-3", Year.of(2018), "Banco de Dados", 12, 9);
        Livro livro5 = new Livro("Redes de Computadores", "Pedro Souza", "Editora E", "978-3-16-148410-4", Year.of(2017), "Redes", 20, 12);
        Livro livro6 = new Livro("Sistemas Operacionais Modernos", "Mariana Pereira", "Editora F", "978-3-16-148410-5", Year.of(2016), "Sistemas Operacionais", 18, 8);
        Livro livro7 = new Livro("Desenvolvimento Web", "Rafael Rodrigues", "Editora G", "978-3-16-148410-6", Year.of(2022), "Desenvolvimento Web", 25, 10);
        Livro livro8 = new Livro("Machine Learning para Iniciantes", "Fernanda Almeida", "Editora H", "978-3-16-148410-7", Year.of(2020), "Machine Learning", 30, 6);
        Livro livro9 = new Livro("Matemática para Engenharia", "Lucas Ferreira", "Editora I", "978-3-16-148410-8", Year.of(2019), "Matemática", 14, 4);
        Livro livro10 = new Livro("Física Quântica", "Larissa Almeida", "Editora J", "978-3-16-148410-9", Year.of(2021), "Física", 22, 11);

        DAO.getLivroDAO().create(livro1);
        DAO.getLivroDAO().create(livro2);
        DAO.getLivroDAO().create(livro3);
        DAO.getLivroDAO().create(livro4);
        DAO.getLivroDAO().create(livro5);
        DAO.getLivroDAO().create(livro6);
        DAO.getLivroDAO().create(livro7);
        DAO.getLivroDAO().create(livro8);
        DAO.getLivroDAO().create(livro9);
        DAO.getLivroDAO().create(livro10);

        Reserva reserva1 = new Reserva("reserva001", leitor1.getId(), livro1.getIsbn(), ReservaStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reserva reserva2 = new Reserva("reserva002", leitor2.getId(), livro2.getIsbn(), ReservaStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reserva reserva3 = new Reserva("reserva003", leitor3.getId(), livro3.getIsbn(), ReservaStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reserva reserva4 = new Reserva("reserva004", leitor4.getId(), livro4.getIsbn(), ReservaStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        Reserva reserva5 = new Reserva("reserva005", leitor5.getId(), livro5.getIsbn(), ReservaStatus.RESERVADO, LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        DAO.getReservaDAO().create(reserva1);
        DAO.getReservaDAO().create(reserva2);
        DAO.getReservaDAO().create(reserva3);
        DAO.getReservaDAO().create(reserva4);
        DAO.getReservaDAO().create(reserva5);

        Emprestimo emprestimo1 = new Emprestimo("admin1", UserPermissao.ADMINISTRADOR, leitor6.getId(), livro6.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, EmprestimoStatus.PENDENTE);
        Emprestimo emprestimo2 = new Emprestimo("admin2", UserPermissao.ADMINISTRADOR, leitor7.getId(), livro7.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, EmprestimoStatus.PENDENTE);
        Emprestimo emprestimo3 = new Emprestimo("bibliotecario1", UserPermissao.BIBLIOTECARIO, leitor8.getId(), livro8.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, EmprestimoStatus.PENDENTE);
        Emprestimo emprestimo4 = new Emprestimo("bibliotecario2", UserPermissao.BIBLIOTECARIO, leitor9.getId(), livro9.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, EmprestimoStatus.PENDENTE);
        Emprestimo emprestimo5 = new Emprestimo("admin3", UserPermissao.ADMINISTRADOR, leitor10.getId(), livro10.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(14), null, 0, EmprestimoStatus.PENDENTE);

        DAO.getEmprestimoDAO().create(emprestimo1);
        DAO.getEmprestimoDAO().create(emprestimo2);
        DAO.getEmprestimoDAO().create(emprestimo3);
        DAO.getEmprestimoDAO().create(emprestimo4);
        DAO.getEmprestimoDAO().create(emprestimo5);
    }
}