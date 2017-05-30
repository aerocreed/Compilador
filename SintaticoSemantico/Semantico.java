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
    public static Stack<Token> pilha = new Stack<>();
    
    private final String MARK = "$";
 
    void empilha(ArrayList<Token> tabela){
        int i=0;
        System.out.println("MARK");
        pilha.push(new Token(MARK, 0));
        //Empilha variáveis de program
        i = empilhaVars(tabela, i);
        while(i < tabela.size()){
            if(tabela.get(i).getToken().equals("procedure")){
                i = empilhaProcedure(tabela, i);
            }
            //verifica uso sem declaração
            if(tabela.get(i).getToken().equals("begin")){
                while(!tabela.get(i).getToken().equals("end")){                                        
                        if(pilha.contains(tabela.get(i).getToken())){
                            //System.out.println("usado: " + tabela.get(i).getToken());
                            for(int j=pilha.size()-1; j>=0; j--){
                                if(pilha.get(j).equals(tabela.get(i).getToken())){
                                    tabela.get(i).setTipo(MARK);
                                }
                            }
                            if(tabela.get(i).getClassificacao().equals("Identificador")){
                                if(tabela.get(i+2).getClassificacao().equals("Identificador")){
                                    if((verificaTipoOp(tabela.get(i), tabela.get(i+2)).equals("Invalida")))
                                        System.err.println("Operacao entre " + tabela.get(i).getTipo() + " e " + tabela.get(i+2).getTipo() + " e' invla'lida");
                                }                            
                        }                            
                        else{
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
        while(!pilha.get(i).getToken().equals(MARK)){
            if(pilha.elementAt(i).equals(token.getToken()) ){
                System.err.println("O token " + token.getToken() + " (" + token.getClassificacao() +") ja' foi declarado na linha " + token.getLinha() + ".");
                return;
            }
            i--;
        }
        pilha.push(tabela.get(index));
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
        pilha.push(tabela.get(i));
        System.out.println("MARK");
        pilha.push(new Token(MARK,0));
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
        while(!pilha.lastElement().getToken().equals(MARK)){
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
    public static Lexico lex = new Lexico();
    public static Sintatico _sintatico = new Sintatico();

    public static void main(String[] args) {
        l.lerArquivo(tabela); // Preenche a tabela de símbolos
        lex.executar(tabela); // Analisa a tabela de símbolos
        _sintatico.lexico = lex;
        _sintatico.executar(tabela);
        Semantico s = new Semantico();
        
        s.empilha(tabela);
    }
}