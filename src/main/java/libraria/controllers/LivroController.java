package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.BookAmountUnderZeroException;
import main.java.libraria.errors.IdAlreadyExistsException;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.model.Livro;

import java.time.Year;
import java.util.Comparator;
import java.util.List;


/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class LivroController {
    /**
     * Cria um livro e o adiciona no acervo da biblioteca, lançando uma exceção.
     *
     * @param titulo         O título do livro.
     * @param autor          Autor do livro.
     * @param editora        A editora de publicação.
     * @param isbn           ISBN único de 13 dígitos.
     * @param dataPublicacao A data em que o livro foi publicado, podendo ser null caso desconhecida.
     * @param categoria      A categoria principal do livro.
     * @param quantidade     Quantidade dos exemplares do livro.
     * @return O livro criado com as informações acrescidas.
     * @throws NotEnoughPermissionException Caso o usuário não seja um administrador ou bibliotecário.
     * @throws IdAlreadyExistsException     Caso o livro com ISBN especificado já exista no acervo.
     * @throws BookAmountUnderZeroException Caso o livro tenha uma quantidade negativa de exemplares.
     */
    public static Livro criarLivro(String titulo, String autor, String editora, String isbn, Year dataPublicacao, String categoria, int quantidade) throws NotEnoughPermissionException, IdAlreadyExistsException, BookAmountUnderZeroException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (quantidade < 0) {
            throw new BookAmountUnderZeroException("Não é permitido quantidades negativas");
        }

        if (DAO.getLivroDAO().findID(isbn) != null) {
            throw new IdAlreadyExistsException("Livro já cadastrado");
        }

        Livro livro = new Livro(titulo, autor, editora, isbn, dataPublicacao, categoria, quantidade, 0);
        DAO.getLivroDAO().create(livro);
        return livro;
    }

    /**
     * Método que remove um livro do acervo da biblioteca. Esse método também remove todas as reservas do livro especificado.
     *
     * @param livro O objeto livro a ser removido.
     * @throws NotEnoughPermissionException Caso o usuário não possua permissão adequada.
     */

    public static void removerLivro(Livro livro) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        DAO.getReservaDAO().deleteAllByBook(livro.getIsbn());
        DAO.getLivroDAO().deleteID(livro.getIsbn());
    }

    /**
     * Método que pesquisa livros por uma chave de pesquisa.
     *
     * @param searchKey A chave de pesquisa a ser usada. Pode ser tanto título quanto autor, editora ou ISBN.
     * @return Uma lista de livros encontrados e ordenados em ordem decrescente de quantidade de vezes pesquisado.
     */
    public static List<Livro> pesquisarLivroPorChave(String searchKey) {
        List<Livro> livrosPesquisados = DAO.getLivroDAO().findBySearchKey(searchKey);
        for (Livro livro : livrosPesquisados) {
            Livro livroUpdate = DAO.getLivroDAO().update(livro);
            livroUpdate.aumentarPesquisa();
        }
        return ordenarPorVezesPesquisado(livrosPesquisados);
    }

    /**
     * Método que ordena os livros em ordem decrescente de vezes pesquisado.
     *
     * @param livroList A lista de livros a ser ordenada.
     * @return Uma lista de livros encontrados e ordenados em ordem decrescente de quantidade de vezes pesquisado.
     */
    public static List<Livro> ordenarPorVezesPesquisado(List<Livro> livroList){
        Comparator<Livro> sortByTimesSearched = Comparator.comparingInt(Livro::getVezesPesquisado);
        livroList.sort(sortByTimesSearched.reversed());
        return livroList;
    }

    /**
     * Método que pesquisa um livro pelo seu ISBN único de 13 dígitos e incrementa um em sua quantidade de vezes pesquisado.
     *
     * @param isbn O ISBN único do livro a ser usado.
     * @return Um único livro que possui o ISBN especificado.
     */
    public static Livro pesquisarLivroPorIsbn(String isbn) {
        Livro livroPesquisado = DAO.getLivroDAO().findID(isbn);
        if(livroPesquisado != null){
            Livro livroUpdate = DAO.getLivroDAO().update(livroPesquisado);
            livroUpdate.aumentarPesquisa();
        }
        return livroPesquisado;
    }

    /**
     * Método que aumenta a quantidade de exemplares de um livro.
     *
     * @param livro O livro a ter a sua quantidade aumentada.
     * @param quantidade A quantidade de exemplares a ser incrementada.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     */
    public static void livroAumentarQuantidade(Livro livro, int quantidade) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        livro.aumentarQuantidade(quantidade);
    }

    /**
     * Método que reduz a quantidade de exemplares de um livro.
     *
     * @param livro O livro a ter a sua quantidade reduzida.
     * @param quantidade A quantidade de exemplares a ser subtraída.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     * @throws BookAmountUnderZeroException Caso a quantidade passe a ser negativa.
     */
    public static void livroReduzirQuantidade(Livro livro, int quantidade) throws NotEnoughPermissionException, BookAmountUnderZeroException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (livro.getQuantidadeDisponiveis() - quantidade < 0) {
            throw new BookAmountUnderZeroException("Não é permitido quantidades negativas");
        }

        livro.aumentarQuantidade(quantidade);
    }

    /**
     * Método que atualiza um livro.
     *
     * @param livro O livro a ter as suas informações atualizadas.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     */
    public static Livro updateLivro(Livro livro) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        return DAO.getLivroDAO().update(livro);
    }
}
