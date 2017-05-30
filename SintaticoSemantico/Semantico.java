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
    
    private final String MARK = "$";
 
    void empilha(ArrayList<Token> tabela){
        int i=0;
        System.out.println("MARK");
        pilha.push(MARK);
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
                        if(tabela.get(i+2).getClassificacao().equals("Identificador")){
                            if((verificaTipoOp(tabela.get(i), tabela.get(i+2)).equals("Invalida")))
                                System.err.println("Operacao entre " + tabela.get(i).getTipo() + " e " + tabela.get(i+2).getTipo() + " e' invla'lida");
                        }
                        else if(!pilha.contains(tabela.get(i).getToken())){
                            System.err.println("Variavel " + tabela.get(i).getToken() + " nao declarada");
                            break;
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
        pilha.push(tabela.get(index).getToken());
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
        System.err.println("Erro: operacao entre " + esquerda.getTipo() + " e " + direita.getTipo() + " e' inv'alida");
        return "Invalida";        
    }
    
    public static ArrayList<Token> tabela = new ArrayList<>();
    public static LeitorArquivo l = new LeitorArquivo();
    public static Lexico a = new Lexico();

    public static void main(String[] args) {
        l.lerArquivo(tabela); // Preenche a tabela de símbolos
        a.executar(tabela); // Analisa a tabela de símbolos
        
        Semantico s = new Semantico();
        
        s.empilha(tabela);
    }
}