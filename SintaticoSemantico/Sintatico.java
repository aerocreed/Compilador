package SintaticoSemantico;

import Lexico.*;

import java.util.ArrayList;

public class Sintatico {
    // *** Pesquise por "VERIFICAR" para avaliar o que falta!

    static int index = 0;
    static Lexico lexico;
    static Token tokenAtual;



    public void executar(ArrayList<Token> tokens, Semantico semantico) { Programa(tokens, semantico); }

    // Executa o próximo token
    public void transicao(ArrayList<Token> tokens, Semantico semantico) {
        if (index < tokens.size()) {
            tokenAtual = tokens.get(index++);
            // VERIFICAR
            //self.__semantic.pushStack(self.__token)
        }
    }



    public void Programa(ArrayList<Token> tokens, Semantico semantico) {
        transicao(tokens, semantico);

        if (tokenAtual.getToken().equals("program")) {
            transicao(tokens, semantico);

            if (tokenAtual.getClassificacao().equals("Identificador")) {
                transicao(tokens, semantico);

                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens, semantico);
                    DeclaracoesVariaveis(tokens, semantico);
                    DeclaracoesSubProgramas();
                    ComandoComposto();
                    transicao(tokens, semantico);

                    if (!tokenAtual.getToken().equals("."))
                        exibirErro(".");
                }
                else
                    exibirErro("Identificador");
            }
            else
                exibirErro(";");
        }
    }

    public void DeclaracoesVariaveis(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("var")) {
            transicao(tokens, semantico);

            if (tokenAtual.getClassificacao().equals("Identificador"))
                ListaDeclaracaoVariaveis(tokens, semantico);
            else
                exibirErro("Identificador");
        }
    }



    // VERIFICAR
    // COM CERTEZA TA BUGADO. LOOP INFINITO!
    public void ListaDeclaracaoVariaveis(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens, semantico);
            ListaDeclaracaoVariaveis2(tokens, semantico);
        }
    }

    private void ListaDeclaracaoVariaveis2(ArrayList<Token> tokens, Semantico semantico) {
        /*
            Como a função lista_declaracao_variaveis() chama a função next_token()
            antes de chamar lista_declaracao_variaveis_ percebi que estavamos olhando
            para os tokens errados comecei a verificar os tokens que estavam alcaçando
            a função lista_identificadores e percebi que estavam errados, quando alterei
            o teste para 1 token atrás a validação começou a funcionar, mas ainda não entendi
            o motivo.
         */
        //if (tokenAtual.getClassificacao().equals("Identificador")) {
        String varnome = tokenAtual.getToken();
        ListaIdentificadores2(tokens, semantico);

        if (tokenAtual.getToken().equals(":")) {
            transicao(tokens, semantico);
            String vartipo = tokenAtual.getToken();
            int varlinha = tokenAtual.getLinha();

            // VERIFICAR
            // self.__semantic.typeStack.append(varnome, vartipo, varlinha))
            Tipo();

            transicao(tokens, semantico);

            if (tokenAtual.getToken().equals(";")) {
                transicao(tokens, semantico);
                ListaDeclaracaoVariaveis2(tokens, semantico);
            }
            else
                exibirErro(";");
        }
        else
            exibirErro(":");
        //}
    }



    public void ListaIdentificadores(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens, semantico);
            ListaDeclaracaoVariaveis2(tokens, semantico);
        }
    }

    public void ListaIdentificadores2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals(",")) {
            transicao(tokens, semantico);

            if (tokenAtual.getClassificacao().equals("Identificador")) {
                // self.__semantic.typeStack.append(tokenAtual.getToken(), null, tokenAtual.getLinha())
                transicao(tokens, semantico);
                ListaIdentificadores2(tokens, semantico);
            }
            else
                exibirErro("Identificador");
        }
    }



    public void DeclaracoesSubProgramas(ArrayList<Token> tokens, Semantico semantico) {
        DeclaracoesSubProgramas2(tokens, semantico);
    }

    public void DeclaracoesSubProgramas2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("procedure")) {
            DeclaracaoSubPrograma();
            transicao(tokens, semantico);
            if (tokenAtual.getToken().equals(";")) {
                transicao(tokens, semantico);
                DeclaracoesSubProgramas2(tokens, semantico);
            }
            else
                exibirErro(";")
        }
    }



    public void DeclaracaoSubPrograma(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("procedure")) {
            transicao(tokens, semantico);
            if (tokenAtual.getClassificacao().equals("Identificador")) {
                transicao(tokens, semantico);
                Argumentos();
                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens, semantico);
                    DeclaracoesVariaveis(tokens, semantico);
                    DeclaracoesSubProgramas(tokens, semantico);
                    ComandoComposto();
                }
                else
                    exibirErro(";");
            }
            else
                exibirErro("identificador");
        }
    }



    public void Argumentos(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens, semantico);
            ListaParametros();
            if (tokenAtual.getToken().equals(")")) {
                transicao(tokens, semantico);
                //return
            }
        }
        else
            exibirErro(")");
    }



    public void ListaParametros(ArrayList<Token> tokens, Semantico semantico) {
        String varnome = tokenAtual.getToken();
        ListaIdentificadores(tokens, semantico);
        if (tokenAtual.getToken().equals(":")) {
            transicao(tokens, semantico);
            String vartipo = tokenAtual.getToken();
            int varlinha = tokenAtual.getLinha();
            //self.__semantic.typeStack.append((id, typ, line))
            Tipo();
            transicao(tokens, semantico);
            ListaParametros(tokens, semantico);
        }
        else
            exibirErro(":");
    }

    public void ListaParametros2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals(";")) {
            transicao(tokens, semantico);
            String varnome = tokenAtual.getToken();
            ListaIdentificadores(tokens, semantico);
            if (tokenAtual.getToken().equals(":")) {
                transicao(tokens, semantico);
                String vartipo = tokenAtual.getToken();
                int varlinha = tokenAtual.getLinha();
                //self.__semantic.typeStack.append((id, typ, line))
                Tipo();
                transicao(tokens, semantico);
                ListaParametros2(tokens, semantico);
            }
        }
    }



    public void ComandoComposto(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void ComandosOpcionais(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void ListaComandos(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void ListaComandos2(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Comando(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void AtivacaoProcedimento(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Else(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void ListaExpressao(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void ListaExpressao2(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Expressao(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void ExpressaoZ(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void ExpressaoSimples(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void ExpressaoSimples2(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Termo(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void Termo2(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Fator(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void FatorExp(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Sinal(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void OpRelacional(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void OpAditivo(ArrayList<Token> tokens, Semantico semantico) {

    }

    public void OpMultiplicativo(ArrayList<Token> tokens, Semantico semantico) {

    }



    public void Tipo() {
        if (tokenAtual.getToken() != "integer" && tokenAtual.getToken() != "real" && tokenAtual.getToken() != "boolean")
            exibirErro("Tipo");
    }



    /* Utilitários */

    public void exibirErro(String tokenEsperado) {
        System.out.println("Erro sintático: \'" + tokenEsperado + "\' esperado em \'" + lexico.getEstado() + "\' (linha: \'" + lexico.getLinha() + "\')");
    }

    /*public String toString() {
        return tokenAtual + "\n" +
    }*/
}
