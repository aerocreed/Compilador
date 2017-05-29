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

/**
 *
 * @author Germano
 */
public class Semantico {
    public static Stack<String> pilha = new Stack<>();
    
    private final String MARK = "$";
    
/*    int empilhaVars(ArrayList<Token> tabela, int index){
        while(!tabela.get(index++).getToken().equals("var"))
            ;   
        while(!tabela.get(index).getToken().equals("procedure") && !tabela.get(index).getToken().equals("begin")){            
            System.out.println("index: " + index + " TOKEN(Vars): " + tabela.get(index).toString());
            if(tabela.get(index).getClassificacao().equals("Identificador")) {
                pilha.push(tabela.get(index).getToken());
            }

            index++;
        }    
        return index;
    }*/
    
    void automato_pilha(ArrayList<Token> tabela, int cont, int i, int estado)
    {                              
        switch (estado) {
        /* Comentários */
            case 0:
                if (tabela.get(i).getToken().equals("procedure")) {
                    estado = 1;
                    ++cont;
                }
                else if (tabela.get(i).getToken().equals("end")) {
                    --cont;
                }
            break;
                
            case 1:
                if (tabela.get(i).getToken().equals("procedure")) {
                    ++cont;
                }
                else if (tabela.get(i).getToken().equals("end")) {
                    estado = 0;
                    --cont;
                }
            break;                
        }
        if(cont == 0 && i == tabela.size()){
            return;
        }
        automato_pilha(tabela, cont, i++, estado);        
    }
    
    
    
    int empilhaProcedure(ArrayList<Token> tabela, int i)
    {
        i++;                
        System.out.println(tabela.get(i).getToken());   
        pilha.push(tabela.get(i).getToken());
        System.out.println("MARK");
        pilha.push(MARK);
        i++;
        while(!tabela.get(i).getToken().equals("end")){
            if(tabela.get(i).getClassificacao().equals("Identificador")){
                System.out.println(tabela.get(i).getToken());
                pilha.push(tabela.get(i).getToken());
            }
            else if(tabela.get(i).getToken().equals("procedure")){
                System.out.println("RECURSIVIDADE");
                empilhaProcedure(tabela, i);                
            }
            
            i++;
        }
        desempilha();
        return i;
    }
    
    void empilhaTokens(ArrayList<Token> tabela){                                
        System.out.println("MARK");
        pilha.push(MARK);
        for(int i=0; i<tabela.size(); i++){
            if(tabela.get(i).getToken().equals("var")){
                i++;
                while(!tabela.get(i).getToken().equals(":")){
                    if(!tabela.get(i).getToken().equals(",")){                        
                        System.out.println(tabela.get(i).getToken());
                        pilha.push(tabela.get(i).getToken());
                    }
                    i++;
                }
            }
            else if(tabela.get(i).getToken().equals("procedure")){
                i = empilhaProcedure(tabela, i);
            }
            else if(tabela.get(i).getToken().equals("(")){
                i++;
                while(!tabela.get(i).getToken().equals(")")){
                    if(!tabela.get(i).getToken().equals(",")){                        
                        System.out.println(tabela.get(i).getToken());
                        pilha.push(tabela.get(i).getToken());
                    }
                    i++;
                }
            }
            else if(tabela.get(i).getToken().equals("end")){   
                System.out.println("MARK");
                //pilha.push(tabela.get(i).getToken());
            }
        }
    
        
        
        /*pilha.push(MARK);
        int index = empilhaVars(tabela, 0);                
        for(int i=index; i<tabela.size(); i++){
            //Novo Escopo
            System.out.println("TOKEN: " + tabela.get(i).getToken());
            if(tabela.get(i).getToken().equals("end")){
                pilha.push(MARK);
            }
            if(tabela.get(i).getToken().equals("procedure")){
                pilha.push(tabela.get(++i).getToken());
                pilha.push(MARK);
                if(tabela.get(++i).getToken().equals("(")){ //Se tem parâmetro
                    while(!tabela.get(i).getToken().equals(")")){
                        if(tabela.get(i).getClassificacao().equals("Identificador")) {
                            pilha.push(tabela.get(i).getToken());
                            index = empilhaVars(tabela, index);
                        }              
                        i++;
                    }
                }
                index = empilhaVars(tabela, index);
            }    
        }  */              
        
        System.out.println("PILHA:");
        System.out.println(pilha.toString());
    }        
    
    void empilhaTeste(ArrayList<Token> tabela){
        int i=0;
        System.out.println("MARK");
        pilha.push(MARK);
        //Empilha variáveis de program
        i = empilhaVars(tabela, i);
        while(i < tabela.size()){
            //System.out.println("TOKEN: " + tabela.get(i).getToken());
            if(tabela.get(i).getToken().equals("procedure")){
                //System.out.println("PROCEDURE ANTES: " + tabela.get(i).getToken());
                i = empilhaProcedureTeste(tabela, i);
                //System.out.println("PROCEDURE DEPOIS: " + tabela.get(i).getToken());
            }
            if(tabela.get(i).getToken().equals("begin")){
                desempilha();
                System.out.println();
            }
            i++;
        }
        
        
    }
    
    int empilhaVars(ArrayList<Token> tabela, int i)
    {
        while(!(tabela.get(i).getToken().equals("procedure") || tabela.get(i).getToken().equals("begin"))){
            if(tabela.get(i).getToken().equals("var")){
                i++;
                while(!tabela.get(i).getToken().equals(":")){
                    if(!tabela.get(i).getToken().equals(",")){                        
                        System.out.println(tabela.get(i).getToken());
                        pilha.push(tabela.get(i).getToken());
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
            if(!tabela.get(i).getToken().equals(",")){                        
                System.out.println(tabela.get(i).getToken());
                pilha.push(tabela.get(i).getToken());
            }
            i++;
        }  
        return i;
    }
    
    int empilhaProcedureTeste(ArrayList<Token> tabela, int i){
        //Empilha identificador do procedimento
        //if(tabela.get(i).getToken().equals("procedure")){
            i++;                
            System.out.println(tabela.get(i).getToken());   
            pilha.push(tabela.get(i).getToken());
            System.out.println("MARK");
            pilha.push(MARK);
            //i++;
            //Empilha os parâmetros, caso tenha
            if(!tabela.get(i+1).getToken().equals(";")){
                i = empilhaParametros(tabela, i);
            }
            i = empilhaVars(tabela, i);
            return i-1;
        //}
    /*
        while(!tabela.get(i).getToken().equals("end.")){
            if(tabela.get(i).getClassificacao().equals("Identificador")){
                System.out.println(tabela.get(i).getToken());
                pilha.push(tabela.get(i).getToken());
            }
            else if(tabela.get(i).getToken().equals("end")){
                System.out.println("FIM DO ESCOPO");
                desempilha();
            }
            else if(tabela.get(i).getToken().equals("procedure")){
                System.out.println("RECURSIVIDADE");
                empilhaProcedure(tabela, i);                
            }
            
            i++;
        }*/
        //quando o token for end, desempilha até mark, inclusive
        //desempilha();
    }
    
    void empilha(ArrayList<Token> tabela)
    {
        pilha.push(MARK);
        for(int i=0; i<tabela.size(); i++){
            if(tabela.get(i).getToken().equals("var")){
                i++;
                while(!(tabela.get(i).getToken().equals("procedure") || tabela.get(i).getToken().equals("end"))){                      
                    System.out.println("WHILE["+i+"]: " + tabela.get(i).getToken());                    
                    while(true){
                        if(tabela.get(i).getToken().equals(":")){
                            i+=2;
                            break;
                        }
                        System.out.println("WHILE2["+i+"]: " + tabela.get(i).getToken());                    
                        if(!tabela.get(i).getToken().equals(",")){
                            System.out.println("EMPILHOU " + tabela.get(i).getToken());
                            pilha.push(tabela.get(i).getToken());
                        }
                        i++;
                    }  
                    i++;
                }                
            }
            else if(tabela.get(i).getToken().equals("procedure")){
                pilha.push(tabela.get(++i).getToken());
            }
        }
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
    
    public static ArrayList<Token> tabela = new ArrayList<>();
    public static LeitorArquivo l = new LeitorArquivo();
    public static Lexico a = new Lexico();

    public static void main(String[] args) {
        l.lerArquivo(tabela); // Preenche a tabela de símbolos
        a.executar(tabela); // Analisa a tabela de símbolos
        
        Semantico s = new Semantico();
        
        s.empilhaTeste(tabela);
    }
}
