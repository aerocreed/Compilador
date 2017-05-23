import Lexico.Automato;
import Lexico.LeitorArquivo;
import Lexico.Simbolo;

import java.util.ArrayList;

public class Principal {
    public static ArrayList<Simbolo> tabela = new ArrayList<>();
    public static LeitorArquivo l = new LeitorArquivo();
    public static Automato a = new Automato();

    public static void main(String[] args) {
        l.lerArquivo(tabela); // Preenche a tabela de símbolos
        a.analisar(tabela); // Analisa a tabela de símbolos

        for (int i = 0; i < tabela.size(); i++)
            System.out.println(tabela.get(i).toString());
    }
}
