package Lexico;

import java.util.ArrayList;

public class Lexico {
    private int estado;
    private boolean estadoFinal;
    private int linha;

    public final String[] tps = {"Palavra Reservada", "Identificador", "Numero Inteiro", "Numero Real", "Delimitador"};
    private String palavrasReservadas[] = {"begin", "boolean", "do", "else", "end", "if", "integer", "not", "procedure", "program", "real", "then", "var", "while", "or", "and"};

    public final int[] aceitacao = {2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
    public final String[] classificacao = {tps[1], tps[2], tps[3], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4], tps[4]};

    public Lexico() {
        estado = 0;
        estadoFinal = false;
        linha = 1;
    }

    public void executar(ArrayList<Token> tokens) {
        // Percorre o código fonte separado em tokens
        for (int i = 0; i < tokens.size(); i++) {
            // Para cada token, percorre caractere por caractere
            for (int j = 0; j < tokens.get(i).getToken().length(); j++)
                estadoFinal = transicao(tokens.get(i).getToken().charAt(j));

            if (estadoFinal) {
                estadoFinal = false;
                tokens.get(i).setClassificacao(TipoToken(tokens.get(i).getToken()));
                estado = 0;
            }
        }
    }

    public String TipoToken(String token) {
        if (isPalavraReservada(token))
            return tps[0];

        for (int i = 0; i < aceitacao.length; i++)
            if (aceitacao[i] == estado)
                return classificacao[i];

        return "Invalido";
    }

    public boolean transicao(char simbolo) {
        int estado = getEstado();
        if (simbolo == '\n')
            linha++;

        switch (estado) {
            /* Comentários */
            case 0:
                if (simbolo == '{')
                    estado = 1;

                if (Character.isLetter(simbolo))
                    estado = 2;
                else if (Character.isDigit(simbolo))
                    estado = 3;
                else if (simbolo == ';')
                    estado = 6;
                else if (simbolo == '.')
                    estado = 7;
                else if (simbolo == ':')
                    estado = 8;
                else if (simbolo == ',')
                    estado = 9;
                else if (simbolo == '(')
                    estado = 10;
                else if (simbolo == ')')
                    estado = 11;
                else if (simbolo == '=')
                    estado = 12;
                else if (simbolo == '<')
                    estado = 13;
                else if (simbolo == '>')
                    estado = 14;
                else if (simbolo == '+')
                    estado = 18;
                else if (simbolo == '-')
                    estado = 19;
                else if (simbolo == '*')
                    estado = 20;
                else if (simbolo == '/')
                    estado = 21;
                break;
            case 1:
                if (simbolo == '}')
                    estado = 0;
                break;

            case 2:
                if (Character.isLetter(simbolo) || Character.isDigit(simbolo) || simbolo == '_')
                    estado = 2;
                break;

            case 3:
                if (Character.isDigit(simbolo))
                    estado = 3;
                else if (simbolo == '.')
                    estado = 4;
                break;

            case 4:
            case 5:
                if (Character.isDigit(simbolo))
                    estado = 5;
                break;

            case 8:
                if (simbolo == '=')
                    estado = 11;
                break;

            case 13:
                if (simbolo == '=')
                    estado = 15;
                else if (simbolo == '<')
                    estado = 17;
                break;

            case 14:
                if (simbolo == '=')
                    estado = 16;
                break;

            // Nenhuma transição é possível
            default:
        }
        setEstado(estado);

        for (int i = 0; i < aceitacao.length; i++)
            if (estado == aceitacao[i])
                return true;
        return false;
    }

    public boolean isPalavraReservada(String token) {
        for (int i = 0; i < palavrasReservadas.length; i++)
            if (token.equals(palavrasReservadas[i]))
                return true;
        return false;
    }

    public int getEstado() {
        return estado;
    }
    public boolean isEstadoFinal() { return estadoFinal; }
    public int getLinha() { return linha; }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public void setEstadoFinal(boolean estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
    public void setLinha(int linha) { this.linha = linha; }
}