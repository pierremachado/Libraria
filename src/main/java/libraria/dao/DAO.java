package main.java.libraria.dao;

import main.java.libraria.dao.Administrador.AdministradorDAO;
import main.java.libraria.dao.Administrador.AdministradorFileDAO;
import main.java.libraria.dao.Administrador.AdministradorListDAO;
import main.java.libraria.dao.Bibliotecario.BibliotecarioDAO;
import main.java.libraria.dao.Bibliotecario.BibliotecarioFileDAO;
import main.java.libraria.dao.Bibliotecario.BibliotecarioListDAO;
import main.java.libraria.dao.Emprestimo.EmprestimoDAO;
import main.java.libraria.dao.Emprestimo.EmprestimoFileDAO;
import main.java.libraria.dao.Emprestimo.EmprestimoListDAO;
import main.java.libraria.dao.Leitor.LeitorDAO;
import main.java.libraria.dao.Leitor.LeitorFileDAO;
import main.java.libraria.dao.Leitor.LeitorListDAO;
import main.java.libraria.dao.Livro.LivroDAO;
import main.java.libraria.dao.Livro.LivroFileDAO;
import main.java.libraria.dao.Livro.LivroListDAO;
import main.java.libraria.dao.Reserva.ReservaDAO;
import main.java.libraria.dao.Reserva.ReservaFileDAO;
import main.java.libraria.dao.Reserva.ReservaListDAO;
import main.java.libraria.model.enums.DAOStatus;

import java.io.File;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class DAO {

    private static AdministradorDAO administradorDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    private static ReservaDAO reservaDAO;
    private static final DAOStatus statusDAO = DAOStatus.FILE;

    public static AdministradorDAO getAdministradorDAO() {
        if (administradorDAO == null) {
            switch (statusDAO) {
                case MEMORY -> administradorDAO = new AdministradorListDAO();
                case FILE -> administradorDAO = new AdministradorFileDAO();
            }
        }
        return administradorDAO;
    }

    public static BibliotecarioDAO getBibliotecarioDAO() {
        if (bibliotecarioDAO == null) {
            switch (statusDAO) {
                case MEMORY -> bibliotecarioDAO = new BibliotecarioListDAO();
                case FILE -> bibliotecarioDAO = new BibliotecarioFileDAO();
            }
        }
        return bibliotecarioDAO;
    }

    public static EmprestimoDAO getEmprestimoDAO() {
        if (emprestimoDAO == null) {
            switch (statusDAO) {
                case MEMORY -> emprestimoDAO = new EmprestimoListDAO();
                case FILE -> emprestimoDAO = new EmprestimoFileDAO();
            }
        }
        return emprestimoDAO;
    }

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null) {
            switch (statusDAO) {
                case MEMORY -> leitorDAO = new LeitorListDAO();
                case FILE -> leitorDAO = new LeitorFileDAO();
            }
        }
        return leitorDAO;
    }

    public static LivroDAO getLivroDAO() {
        if (livroDAO == null) {
            switch (statusDAO) {
                case MEMORY -> livroDAO = new LivroListDAO();
                case FILE -> livroDAO = new LivroFileDAO();
            }
        }
        return livroDAO;
    }

    /**
     * @return Instância do DAO.
     */

    public static ReservaDAO getReservaDAO() {
        if (reservaDAO == null) {
            switch (statusDAO) {
                case MEMORY -> reservaDAO = new ReservaListDAO();
                case FILE -> reservaDAO = new ReservaFileDAO();
            }
        }
        return reservaDAO;
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
        administradorDAO = null;
        bibliotecarioDAO = null;
        emprestimoDAO = null;
        leitorDAO = null;
        livroDAO = null;
        reservaDAO = null;

        if (statusDAO.equals(DAOStatus.FILE)) { // para fins de teste
            File pasta = new File("storage" + File.separator + "test");
            deleteDir(pasta);
        }
    }
}