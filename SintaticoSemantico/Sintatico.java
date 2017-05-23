package SintaticoSemantico;

import Lexico.Simbolo;

import java.util.ArrayList;

public class Sintatico {
    static int index = 0;
    
    public void Programa(ArrayList<Simbolo> tabela) {
        if(tabela.get(index).getToken().equals("program")) {
            index++;

            if(tabela.get(index).getClassificacao().equals("Identificador")) {
                index++;

                if(tabela.get(index).getToken().equals(";")) {
                    index++;
                    DeclaracoesVariaveis(tabela);
                    DeclaraçõesDeSubProgramas();
                    ComandoComposto();

                    if(tabela.get(index++).getToken().equals(".")){
                        //OK
                    }
                }                    
            }
        }
    }

    public void DeclaracoesVariaveis(ArrayList<Simbolo> tabela) {
       if(tabela.get(index).getToken().equals("var")) {
           index++;
           ListaDeclaracoesVariaveis();
           if(tabela.get(index).getToken().isEmpty())
               return;
       }
    }

    public void DeclaraçõesDeSubProgramas() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void ListaDeclaracoesVariaveis() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void ComandoComposto() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
