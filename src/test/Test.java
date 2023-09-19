package test;

import model.*;

public class Test {
    public static void main(String[] args){
        Administrador adm = new Administrador("admin", "123", "Administrador", "senha");
        Bibliotecario bib = new Bibliotecario("bibliotecario", "213", "Bibliotecário", "ahnes");
        Leitor leitor = new Leitor("leitor", "321", "Leitor", "password", "UEFS", "123456789", LeitorStatus.LIBERADO);

        System.out.println(adm.toString());
        System.out.println(bib.toString());
        System.out.println(leitor.toString());
    }
}
