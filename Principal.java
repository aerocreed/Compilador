import Lexico.*;

import java.util.ArrayList;

public class Principal {
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static LeitorArquivo leitor = new LeitorArquivo();
    public static Lexico lexico = new Lexico();

    public static void main(String[] args) {
        leitor.lerArquivo(tokens); // Preenche lexico tokens de símbolos
        lexico.executar(tokens); // Analisa lexico tokens de símbolos

        for (int i = 0; i < tokens.size(); i++)
            System.out.println(tokens.get(i).toString());
    }
}
