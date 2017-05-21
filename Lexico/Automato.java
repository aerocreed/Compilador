package Lexico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Germano
 */
public class Automato {
    
    private int estado;
    public final int[] aceitacao = {2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
    public final String[] tps = {"Palavra Reservada", "Identificador", "Numero Inteiro", "Numero Real", "Delimitador"};                                         
    public final String[] classificacao = {tps[1], tps[2], tps[3], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4]};
    private boolean estado_final;
    private String palavras_reservadas[] = {"begin","boolean","do","else","end","if","integer","not","procedure",
                                            "program","real","then","var","while", "or", "and"};
    private int linha;
    
    public Automato(){
        estado = 0;
        estado_final = false;
        linha = 1;
    }        
    
    public void Analise(Automato a){
        //Percorre o código fonte separado em tokens
        for(int i=0; i<TabelaDeSimbolos.tabela.size(); i++){
            //Para cada token, percorre caractere por caractere
            for(int j=0; j< TabelaDeSimbolos.tabela.get(i).getToken().length(); j++){     
                estado_final = Transicao(a, TabelaDeSimbolos.tabela.get(i).getToken().charAt(j));                
            }
            if(estado_final){
                estado_final = false;
                TabelaDeSimbolos.tabela.get(i).setClassificacao(TipoToken(TabelaDeSimbolos.tabela.get(i).getToken()));
                estado = 0;
            }
        }
    }
    
    public String TipoToken(String token){
        if(isPalavraReservada(token)){
            return tps[0];
        }
        for(int i=0; i<aceitacao.length; i++){
            if(aceitacao[i] == estado){
                return classificacao[i];
            }
        }
        return "Invalido";
        
    }
    
    public boolean Transicao(Automato a, char simbolo){
        int estado = a.getEstado();
        if(simbolo == '\n')
            linha++;
        switch(estado){
            case 0:
                if(simbolo == '{'){
                    estado = 1;
                }
                if(Character.isLetter(simbolo)){
                    estado = 2;
                }
                else if(Character.isDigit(simbolo)){
                    estado = 3;
                }
                else if(simbolo == ';'){
                    estado = 6;
                }
                else if(simbolo == '.'){
                    estado = 7;
                }
                else if(simbolo == ':'){
                    estado = 8;
                }
                else if(simbolo == ','){
                    estado = 9;
                }             
                else if(simbolo == '('){
                    estado = 10;
                }
                else if(simbolo == ')'){
                    estado = 11;
                }
                else if(simbolo == '='){
                    estado = 12;
                }
                else if(simbolo == '<'){
                    estado = 13;
                }
                else if(simbolo == '>'){
                    estado = 14;
                }
                else if(simbolo == '+'){
                    estado = 18;
                }
                else if(simbolo == '-'){
                    estado = 19;
                }
                else if(simbolo == '*'){
                    estado = 20;
                }
                else if(simbolo == '/'){
                    estado = 21;
                }
                break;
            case 1:
                if(simbolo == '}')
                    estado = 0;
                break;
            case 2:
                if(Character.isLetter(simbolo) || Character.isDigit(simbolo) || simbolo == '_')
                    estado = 2;
            break;
            case 3:
                if(Character.isDigit(simbolo))
                    estado = 3;
                else if(simbolo == '.')
                    estado = 4;
            break;
            case 4:
            case 5:
                if(Character.isDigit(simbolo))
                    estado = 5;
            break;
            case 8:
                if(simbolo == '=')
                    estado = 11;
            break;
            case 13:
                if(simbolo == '=')
                    estado = 15;
                else if(simbolo == '<')
                    estado = 17;
            break;
            case 14:
                if(simbolo == '=')
                    estado = 16;
            break;
                                
            //nenhuma transição é possível
            default:
        }
        a.setEstado(estado);
        for(int i=0; i<aceitacao.length; i++){
            if(estado==aceitacao[i]){
                return true;                
            }
        }
        return false;
    }

    public boolean isPalavraReservada(String token){
        for(int i=0; i< palavras_reservadas.length; i++){
            if(token.equals(palavras_reservadas[i]))
                return true;
        }
        return false;
    }
    
    public boolean isEstado_final() {
        return estado_final;
    }

    public void setEstado_final(boolean estado_final) {
        this.estado_final = estado_final;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}