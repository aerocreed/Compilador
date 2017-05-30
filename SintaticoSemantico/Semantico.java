/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SintaticoSemantico;

import Lexico.LeitorArquivo;
import Lexico.Lexico;
import Lexico.Token;
import java.util.ArrayList;
import java.util.Stack;

public class Semantico {
    public static Stack<String> pilha = new Stack<>();
    final String OPERADORES_NUM = "+|*|-|/|";
    final String OPERADORES_BOOL = "->|<>|>=|<=";
    
    private final String MARK = "$";
 
    void empilha(ArrayList<Token> tabela){
        int i=0;
        System.out.println("MARK");
        pilha.push(MARK);
        String tipoRes = "";
        String operador = "";
        Token aux = null, aux2 = null;
        //Empilha variáveis de program
        i = empilhaVars(tabela, i);
        while(i < tabela.size()){
            if(tabela.get(i).getToken().equals("procedure")){
                i = empilhaProcedure(tabela, i);
            }
            //verifica uso sem declaração
            if(tabela.get(i).getToken().equals("begin")){
                while(!tabela.get(i).getToken().equals("end")){                                        
                        if(tabela.get(i).getClassificacao().equals("Identificador")){
                            //System.out.println("usado: " + _tabela.get(i).getToken());
                            if(pilha.contains(tabela.get(i).getToken())){
                                for(int j=pilha.size()-1; j>=0; j--){
                                    if(tabela.get(i).getToken().equals(pilha.get(j))){
                                        tipoRes = tabela.get(i).getTipo();
                                        break;
                                    }
                                }
                                i++;
                                if(tabela.get(i).getToken().equals(":=")){
                                    i++;
                                    if(tabela.get(i).getClassificacao().equals("Identificador")){
                                        //Adiciona o tipo a variável usada
                                        for(int j=pilha.size()-1; j>=0; j--){
                                            if(tabela.get(i).getToken().equals(pilha.get(j))){
                                                aux = tabela.get(i);
                                                break;
                                            }
                                        }
                                        i++;                                        
                                        if(tabela.get(i).getClassificacao().equals("Delimitador")){
                                            operador = tabela.get(i).getToken();
                                            i++;
                                            for(int j=pilha.size()-1; j>=0; j--){
                                                if(tabela.get(i).getToken().equals(pilha.get(j))){
                                                    aux2 = tabela.get(i);
                                                    break;
                                                }
                                            }
                                        }
                                        
                                        String res = verificaTipoOp(aux, aux2);
                                        System.out.println("RES: " + res);
                                        if(aux.getTipo().equals("boolean") && aux2.getTipo().equals("boolean")){
                                            if(!operador.matches(OPERADORES_BOOL)){
                                                //System.out.println("")
                                            }
                                        }
                                        else if((aux.getTipo().equals("integer") || aux.getTipo().equals("real"))
                                                &&
                                            aux2.getTipo().equals("integer") && aux2.getTipo().equals("real")){
                                            //verifica se o operador é para tipos numericos
                                        }
                                        else if(!res.equals(tipoRes)){
                                            System.err.println("Operacao entre " + aux + " e " + aux2 + " e' invla'lida");
                                        }                                                                                
                                    }
                                }/*{
                                if(tabela.get(i+2).getClassificacao().equals("Identificador")){
                                    if((verificaTipoOp(tabela.get(i), tabela.get(i+2)).equals("Invalida")))
                                        System.err.println("Operacao entre " + tabela.get(i).getTipo() + " e " + tabela.get(i+2).getTipo() + " e' invla'lida");
                                } */                           
                            }                            
                            else{
                                System.err.println("Variavel " + tabela.get(i).getToken() + " nao declarada");                            
                            }
                    }
                    i++;
                }                
                desempilha();
                System.out.println();
            }
            i++;
        }                
    }
    
    void identificadoresMesmoNome(Token token, int index){
        int i=pilha.size()-1;
        while(!pilha.get(i).equals(MARK)){
            if(pilha.elementAt(i).equals(token.getToken()) ){
                System.err.println("O token " + token.getToken() + " (" + token.getClassificacao() +") ja' foi declarado na linha " + token.getLinha() + ".");
                return;
            }
            i--;
        }
        pilha.push(_tabela.get(index).getToken());
    }
    
    int empilhaVars(ArrayList<Token> tabela, int i)
    {
        while(!(tabela.get(i).getToken().equals("procedure") || tabela.get(i).getToken().equals("begin"))){
            if(tabela.get(i).getToken().equals("var")){
                i++;
                while(!tabela.get(i).getToken().equals(":")){
                    if(!tabela.get(i).getToken().equals(",")){                                                
                        identificadoresMesmoNome(tabela.get(i), i);
                        System.out.println(tabela.get(i).getToken());
                    }
                    i++;
                }
            }
            i++;
        }    
        return i;
    }
    
    int empilhaParametros(ArrayList<Token> tabela, int i)
    {
        while(!(tabela.get(i).getToken().equals("("))){
            i++;
        }
        //Posiciona o índice na posição do primeiro parâmetro
        i++;
        //Empilha os parâmetros
        while(!tabela.get(i).getToken().equals(")")){
            if(tabela.get(i).getClassificacao().equals("Identificador")){                        
                System.out.println(tabela.get(i).getToken());
                identificadoresMesmoNome(tabela.get(i), i);
            }
            i++;
        }  
        return i;
    }
    
    int empilhaProcedure(ArrayList<Token> tabela, int i){
        //Empilha identificador do procedimento
        i++;                
        System.out.println(tabela.get(i).getToken());   
        pilha.push(tabela.get(i).getToken());
        System.out.println("MARK");
        pilha.push(MARK);
        //Empilha os parâmetros, caso tenha
        if(!tabela.get(i+1).getToken().equals(";")){
            i = empilhaParametros(tabela, i);
        }
        //Empilha as variáveis do procedimento
        i = empilhaVars(tabela, i);
        return i-1;
    }    
    
    int desempilha()
    {
        int cont = 0;
        //Desempilha tudo até MARK
        while(!pilha.lastElement().equals(MARK)){
            System.out.println("Desempilhou " + pilha.lastElement());
            pilha.pop();
            cont ++;
        }
        //Desempilha MARK
        System.out.println("Desempilhou " + pilha.lastElement());
        pilha.pop();
        return cont+1;
    }
    
    String verificaTipoOp(Token esquerda, Token direita)
    {
        if(esquerda.getTipo().equals("integer") && direita.getTipo().equals("integer")){            
            return "integer";
        }
        else if(esquerda.getTipo().equals("integer") && direita.getTipo().equals("real")){
            return "real";
        }
        else if(esquerda.getTipo().equals("real") && direita.getTipo().equals("integer")){
            return "real";
        }
        else if(esquerda.getTipo().equals("real") && direita.getTipo().equals("real")){
            return "real";
        }
        else if(esquerda.getTipo().equals("boolean") && direita.getTipo().equals("boolean")){
            return "boolean";
        }        
        return "Invalida";        
    }

    public static ArrayList<Token> _tabela = new ArrayList<>();
    public static LeitorArquivo _leitorArquivo = new LeitorArquivo();
    public static Sintatico _sintatico = new Sintatico(new Lexico());
    public static Semantico _semantico = new Semantico();

    public static void main(String[] args) {
        _leitorArquivo.lerArquivo(_tabela); // Preenche a _tabela de símbolos
        _sintatico.executar(_tabela); // Preenche os tipos dos tokens variaveis e parametros, exceto onde estao usadas; verifica erros sintaticos
        _semantico.empilha(_tabela);
    }
}