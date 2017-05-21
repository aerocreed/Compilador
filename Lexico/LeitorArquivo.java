package Lexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LeitorArquivo {

    final String COMENTARIOS = "\\{(.|\\n)+}";
    final String DELIMITADORES = "('.+'|\\d+\\.\\d+|\\w+|<>|:=|>=|<=|[.,:;()+\\-*/><])"; //número real foi usado como delimitador aqui para que o '.' não separasse em dois inteiros
    final String SEP = "<SEP>"; //usado temporiamente para a separação dos tokens
    
    public String RemoveComentarios(String s){
        // Remove comments
        s = s.replaceAll(COMENTARIOS, "");
        return s;
    }
    
    public String[] SeparaTokens(String s){              
        // Add spaces to these words and symbols
        s = s.replaceAll(DELIMITADORES, "$1"+SEP); // Add spaces to these words and symbols        

       // Remove spaces that is not between '...'
        s = s.replaceAll("('.+')|\\s+", "$1");        

        // Splitter
        String[] tokens = s.split(SEP);
        return tokens;
    }
    
	public void lerArquivo() {
            Scanner ler = new Scanner(System.in);
            String[] tokens = {""};
            String texto = "";
            String tmp;
            int linha = 1;

	    String caminho_arquivo = "src/teste.p";
	    File arquivo = new File(caminho_arquivo);
            
	     
	    try {
	    	ler = new Scanner(arquivo);//muda o Scanner, que agora passa a ler o arquivo
	    	
	    	while (ler.hasNext()) {
                    texto += ler.nextLine() + "\n";
                }
                texto = RemoveComentarios(texto);
                ler = new Scanner(texto);
                while (ler.hasNext()){
                    tmp = ler.nextLine();
                    tokens = SeparaTokens(tmp);
                    for(int i=0; i<tokens.length; i++){ 
                        if(!tokens[i].isEmpty()) {
                            TabelaDeSimbolos.tabela.add(new ElementoDaTabelaDeSimbolos(tokens[i], "", linha));
                        }

                    }
                    linha++;
	        }                
                
		} catch (FileNotFoundException e) {
			System.out.println("O Arquivo n�o foi encontrado! Abortando execu��o");
			System.exit(1);
		}
		catch (IOException e){	
			System.out.println("Erro na leitura do Arquivo! Abortando execu��o");
			System.exit(1);
		}
	    
	}
}