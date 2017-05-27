package Lexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeitorArquivo {
    final String COMENTARIOS = "\\{(.|\\n)+}";
    final String DELIMITADORES = "('.+'|\\d+\\.\\d+|\\w+|->|<>|:=|>=|<=|[.,:;()+\\-*/><])"; // Número real foi usado como delimitador aqui para que o '.' não separasse em dois inteiros
    final String SEP = "<SEP>"; // Usado temporiamente para a separação dos tokens

    public String RemoveComentarios(String s) {
        s = s.replaceAll(COMENTARIOS, "");
        return s;
    }

    public String[] SeparaTokens(String s) {
        // Adiciona espaços após cada token e símbolos
        s = s.replaceAll(DELIMITADORES, "$1" + SEP); // Adicionar espaços para as palavras e símbolos

        // Remove espaços que não estejam entre apóstrofos ('...')
        s = s.replaceAll("('.+')|\\s+", "$1");

        // Retorna uma lista quebrando no <SEP>
        String[] tokens = s.split(SEP);
        return tokens;
    }

    public void lerArquivo(ArrayList<Token> tokens) {
        Scanner ler;
        String[] tokensString;
        String texto = "";
        String tmp;
        int linha = 1;

        String caminho_arquivo = "src/exemplo.p";
        File arquivo = new File(caminho_arquivo);


        try {
            ler = new Scanner(arquivo);

            while (ler.hasNext())
                texto += ler.nextLine() + "\n";

            texto = RemoveComentarios(texto);
            ler = new Scanner(texto);

            while (ler.hasNext()) {
                tmp = ler.nextLine();
                tokensString = SeparaTokens(tmp);

                for (int i = 0; i < tokensString.length; i++)
                    if (!tokensString[i].isEmpty())
                        tokens.add(new Token(tokensString[i], "", linha));

                linha++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado.");
            System.exit(1);
        }
    }
}