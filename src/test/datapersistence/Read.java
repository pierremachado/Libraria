package test.datapersistence;

import main.java.libraria.dao.DAO;

public class Read {
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
