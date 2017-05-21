/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SintaticoSemantico;

import Lexico.TabelaDeSimbolos;

/**
 *
 * @author Germano
 */
public class Sintatico {
    static int index = 0;
    
    public void Programa(){
        if(TabelaDeSimbolos.tabela.get(index).getToken().equals("program")){
            index++;
            if(TabelaDeSimbolos.tabela.get(index).getClassificacao().equals("Identificador")){
                index++;
                if(TabelaDeSimbolos.tabela.get(index).getToken().equals(";")){
                    index++;
                    DeclaracoesVariaveis();
                    DeclaraçõesDeSubProgramas();
                    ComandoComposto();
                    if(TabelaDeSimbolos.tabela.get(index++).getToken().equals(".")){
                        //OK
                    }
                }                    
            }
        }
    }

    public void DeclaracoesVariaveis() {
       if(TabelaDeSimbolos.tabela.get(index).getToken().equals("var")){
           index++;
           ListaDeclaracoesVariaveis();
           if(TabelaDeSimbolos.tabela.get(index).getToken().isEmpty()) 
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
