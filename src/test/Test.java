package test;

import model.*;
import model.enums.EmprestimoStatus;
import model.enums.LeitorStatus;
import model.enums.ReservaStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        System.out.println("TESTE USUÁRIOS\n");
        Administrador adm = new Administrador("admin", "123", "Administrador", "senha");
        Bibliotecario bib = new Bibliotecario("bibliotecario", "213", "Bibliotecário", "ahnes");
        Leitor leitor = new Leitor("leitor", "321", "Leitor", "password", "UEFS", "123456789", LeitorStatus.LIBERADO);

        System.out.println(adm.toString());
        System.out.println(bib.toString());
        System.out.println(leitor.toString());

        System.out.println("\nTESTE LIVRO, RESERVAS, EMPRÉSTIMO");
        Livro livro = new Livro("A Culpa é das Estrelas", "John Green", "Intrínseca", "978-8580572261", "2014", new ArrayList<>(), 20, 0);
        Reserva reserva = new Reserva(leitor, livro, ReservaStatus.ESPERA, LocalDateTime.now());

        System.out.println(livro.toString());
        System.out.println(reserva.toString());

        reserva.setStatus(ReservaStatus.LIBERADO);

        Emprestimo emprestimo = new Emprestimo(bib, leitor, livro, LocalDateTime.now(), LocalDateTime.now().plusDays(7), 0, EmprestimoStatus.EMPRESTADO, reserva);
        reserva.setStatus(ReservaStatus.EMPRESTADO);

        System.out.println(emprestimo.toString());
        System.out.println(reserva.toString());
    }
}
