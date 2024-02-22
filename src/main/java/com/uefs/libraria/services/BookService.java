package com.uefs.libraria.services;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.BookAmountUnderZeroException;
import com.uefs.libraria.exceptions.IdAlreadyExistsException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Book;

import java.time.Year;
import java.util.Comparator;
import java.util.List;


/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class BookService {

    private static Book selectedBook;

    public static Book getSelectedBook() {
        return selectedBook;
    }

    public static void setSelectedBook(Book selectedBook) {
        BookService.selectedBook = selectedBook;
    }


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
    public static Book criarLivro(String titulo, String autor, String editora, String isbn, Year dataPublicacao,
                                  String categoria, int quantidade)
            throws NotEnoughPermissionException, IdAlreadyExistsException, BookAmountUnderZeroException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (quantidade < 0) {
            throw new BookAmountUnderZeroException("Não é permitido quantidades negativas");
        }

        if (DAO.getLivroDAO().findID(isbn) != null) {
            throw new IdAlreadyExistsException("Livro já cadastrado");
        }

        Book book = new Book(titulo, autor, editora, isbn, dataPublicacao, categoria, quantidade, 0);
        DAO.getLivroDAO().create(book);
        return book;
    }

    /**
     * Método que remove um livro do acervo da biblioteca. Esse método também remove todas as reservas do livro especificado.
     *
     * @param book O objeto livro a ser removido.
     * @throws NotEnoughPermissionException Caso o usuário não possua permissão adequada.
     */

    public static void removerLivro(Book book) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        DAO.getReservaDAO().deleteAllByBook(book.getIsbn());
        DAO.getLivroDAO().deleteID(book.getIsbn());
    }

    /**
     * Método que pesquisa livros por uma chave de pesquisa.
     *
     * @param searchKey A chave de pesquisa a ser usada. Pode ser tanto título quanto autor, editora ou ISBN.
     * @return Uma lista de livros encontrados e ordenados em ordem decrescente de quantidade de vezes pesquisado.
     */
    public static List<Book> pesquisarLivroPorChave(String searchKey) {
        List<Book> livrosPesquisados = DAO.getLivroDAO().findBySearchKey(searchKey);
        for (Book book : livrosPesquisados) {
            Book bookUpdate = DAO.getLivroDAO().update(book);
            bookUpdate.aumentarPesquisa();
        }
        return ordenarPorVezesPesquisado(livrosPesquisados);
    }

    /**
     * Método que ordena os livros em ordem decrescente de vezes pesquisado.
     *
     * @param bookList A lista de livros a ser ordenada.
     * @return Uma lista de livros encontrados e ordenados em ordem decrescente de quantidade de vezes pesquisado.
     */
    public static List<Book> ordenarPorVezesPesquisado(List<Book> bookList) {
        Comparator<Book> sortByTimesSearched = Comparator.comparingInt(Book::getVezesPesquisado);
        bookList.sort(sortByTimesSearched.reversed());
        return bookList;
    }

    /**
     * Método que pesquisa um livro pelo seu ISBN único de 13 dígitos e incrementa um em sua quantidade de vezes pesquisado.
     *
     * @param isbn O ISBN único do livro a ser usado.
     * @return Um único livro que possui o ISBN especificado.
     */
    public static Book pesquisarLivroPorIsbn(String isbn) {
        Book bookPesquisado = DAO.getLivroDAO().findID(isbn);
        if (bookPesquisado != null) {
            Book bookUpdate = DAO.getLivroDAO().update(bookPesquisado);
            bookUpdate.aumentarPesquisa();
        }
        return bookPesquisado;
    }

    /**
     * Método que aumenta a quantidade de exemplares de um livro.
     *
     * @param book      O livro a ter a sua quantidade aumentada.
     * @param quantidade A quantidade de exemplares a ser incrementada.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     */
    public static void livroAumentarQuantidade(Book book, int quantidade) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        book.aumentarQuantidade(quantidade);
    }

    /**
     * Método que reduz a quantidade de exemplares de um livro.
     *
     * @param book      O livro a ter a sua quantidade reduzida.
     * @param quantidade A quantidade de exemplares a ser subtraída.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     * @throws BookAmountUnderZeroException Caso a quantidade passe a ser negativa.
     */
    public static void livroReduzirQuantidade(Book book, int quantidade)
            throws NotEnoughPermissionException, BookAmountUnderZeroException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (book.getQuantidadeDisponiveis() - quantidade < 0) {
            throw new BookAmountUnderZeroException("Não é permitido quantidades negativas");
        }

        book.aumentarQuantidade(quantidade);
    }

    /**
     * Método que atualiza um livro.
     *
     * @param book O livro a ter as suas informações atualizadas.
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador.
     */
    public static Book updateLivro(Book book) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        return DAO.getLivroDAO().update(book);
    }
}
