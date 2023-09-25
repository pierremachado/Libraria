package main.dao;

import main.dao.Administrador.AdministradorDAO;
import main.dao.Administrador.AdministradorListDAO;
import main.dao.Bibliotecario.BibliotecarioDAO;
import main.dao.Bibliotecario.BibliotecarioListDAO;
import main.dao.Categoria.CategoriaDAO;
import main.dao.Categoria.CategoriaListDAO;
import main.dao.Emprestimo.EmprestimoDAO;
import main.dao.Emprestimo.EmprestimoListDAO;
import main.dao.Leitor.LeitorDAO;
import main.dao.Leitor.LeitorListDAO;
import main.dao.Livro.LivroDAO;
import main.dao.Livro.LivroListDAO;
import main.dao.Reserva.ReservaDAO;
import main.dao.Reserva.ReservaListDAO;

public class DAO {

    private static AdministradorDAO administradorDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static CategoriaDAO categoriaDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    private static ReservaDAO reservaDAO;

    public static AdministradorDAO getAdministradorDAO() {
        if (administradorDAO == null) {
            administradorDAO = new AdministradorListDAO();
        }
        return administradorDAO;
    }

    public static BibliotecarioDAO getBibliotecarioDAO() {
        if (bibliotecarioDAO == null) {
            bibliotecarioDAO = new BibliotecarioListDAO();
        }
        return bibliotecarioDAO;
    }

    public static CategoriaDAO getCategoriaDAO() {
        if (categoriaDAO == null) {
            categoriaDAO = new CategoriaListDAO();
        }
        return categoriaDAO;
    }

    public static EmprestimoDAO getEmprestimoDAO() {
        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoListDAO();
        }
        return emprestimoDAO;
    }

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null) {
            leitorDAO = new LeitorListDAO();
        }
        return leitorDAO;
    }

    public static LivroDAO getLivroDAO() {
        if (livroDAO == null) {
            livroDAO = new LivroListDAO();
        }
        return livroDAO;
    }

    public static ReservaDAO getReservaDAO() {
        if (reservaDAO == null) {
            reservaDAO = new ReservaListDAO();
        }
        return reservaDAO;
    }
}