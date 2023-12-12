package test.java.com.uefs.libraria.datapersistence;

import main.java.com.uefs.libraria.dao.DAO;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class ReadTest {
    public static void main(String[] args) {
        System.out.println("Administradores:");
        System.out.println(DAO.getAdministradorDAO().findAll());

        System.out.println("\nBibliotecários:");
        System.out.println(DAO.getBibliotecarioDAO().findAll());

        System.out.println("\nLeitores:");
        System.out.println(DAO.getLeitorDAO().findAll());

        System.out.println("\nLivros:");
        System.out.println(DAO.getLivroDAO().findAll());

        System.out.println("\nReservas:");
        System.out.println(DAO.getReservaDAO().findAll());

        System.out.println("\nEmpréstimos:");
        System.out.println(DAO.getEmprestimoDAO().findAll());
    }
}
