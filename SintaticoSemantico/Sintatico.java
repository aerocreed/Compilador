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
            tokenAtual = tokens.get(index);
            // VERIFICAR
            //self.__semantic.pushStack(self.__token)
            index++;
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
                    DeclaracoesSubProgramas(tokens, semantico);
                    ComandoComposto(tokens, semantico);
                    transicao(tokens, semantico);

                    if (!tokenAtual.getToken().equals("."))
                        exibirErro(".");
                }
                else
                    exibirErro("program");
            }
            else
                exibirErro("Identificador");
        }
        else
            exibirErro(";");
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



    public void ListaDeclaracaoVariaveis(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens, semantico);
            ListaIdentificadores(tokens, semantico);
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
            DeclaracaoSubPrograma(tokens, semantico);
            transicao(tokens, semantico);
            if (tokenAtual.getToken().equals(";")) {
                transicao(tokens, semantico);
                DeclaracoesSubProgramas2(tokens, semantico);
            }
            else
                exibirErro(";");
        }
    }



    public void DeclaracaoSubPrograma(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("procedure")) {
            transicao(tokens, semantico);
            if (tokenAtual.getClassificacao().equals("Identificador")) {
                transicao(tokens, semantico);
                Argumentos(tokens, semantico);
                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens, semantico);
                    DeclaracoesVariaveis(tokens, semantico);
                    DeclaracoesSubProgramas(tokens, semantico);
                    ComandoComposto(tokens, semantico);
                }
                else
                    exibirErro(";");
            }
            else
                exibirErro("Identificador");
        }
    }



    public void Argumentos(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens, semantico);
            ListaParametros(tokens, semantico);
            if (tokenAtual.getToken().equals(")")) {
                transicao(tokens, semantico);
                //return
            }
            else
                exibirErro(")");
        }
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
            ListaParametros2(tokens, semantico);
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
            // Faltou?
            //else
                //exibirErro(":");
        }
    }



    public void ComandoComposto(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("begin")) {
            transicao(tokens, semantico);
            ComandosOpcionais(tokens, semantico);
            if (!tokenAtual.getToken().equals("end"))
                exibirErro("end");
        }
        else
            exibirErro("begin");
    }

    public void ComandosOpcionais(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getClassificacao().equals("Identificador") ||
            tokenAtual.getToken().equals("while") ||
            tokenAtual.getToken().equals("if") ||
            tokenAtual.getToken().equals("begin"))
            ListaComandos(tokens, semantico);
    }



    public void ListaComandos(ArrayList<Token> tokens, Semantico semantico) {
        Comando(tokens, semantico);
        ListaComandos2(tokens, semantico);
    }

    public void ListaComandos2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals(";")) {
            transicao(tokens, semantico);
            Comando(tokens, semantico);
            ListaComandos2(tokens, semantico);
        }
    }



    public void Comando(ArrayList<Token> tokens, Semantico semantico) {
        // Identificadores
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens, semantico);

            if (tokenAtual.getToken().equals(":=")) {
                transicao(tokens, semantico);
                Expressao(tokens, semantico);
            } else if (tokenAtual.getToken().equals("(")) {
                AtivacaoProcedimento(tokens, semantico);
                transicao(tokens, semantico);
            }
        }
        // Comando composto
        else if (tokenAtual.getToken().equals("begin")) {
            ComandoComposto(tokens, semantico);
        }
        // -> if
        else if (tokenAtual.getToken().equals("if")) {
            transicao(tokens, semantico);
            Expressao(tokens, semantico);

            if (tokenAtual.getToken().equals("then")) {
                transicao(tokens, semantico);
                Comando(tokens, semantico);
                Else(tokens, semantico);
            }
            else
                exibirErro("then");
        }
        // -> while
        else if (tokenAtual.getToken().equals("while")) {
            transicao(tokens, semantico);
            Expressao(tokens, semantico);

            if (tokenAtual.getToken().equals("do")) {
                transicao(tokens, semantico);
                Comando(tokens, semantico);
            }
            else
                exibirErro("do");
        }
        // If it's not a command
        else
            exibirErro("Comando");
    }



    public void AtivacaoProcedimento(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens, semantico);
            ListaExpressao(tokens, semantico);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
    }



    public void Else(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("else")) {
            transicao(tokens, semantico);
            Comando(tokens, semantico);
        }
    }



    public void ListaExpressao(ArrayList<Token> tokens, Semantico semantico) {
        Expressao(tokens, semantico);

        if (!tokenAtual.getToken().equals(")"))
            ListaExpressao2(tokens, semantico);
    }

    public void ListaExpressao2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals(",")) {
            transicao(tokens, semantico);
            Expressao(tokens, semantico);
            ListaExpressao2(tokens, semantico);
        }
    }



    public void Expressao(ArrayList<Token> tokens, Semantico semantico) {
        ExpressaoSimples(tokens, semantico);
        ExpressaoZ(tokens, semantico);
    }



    public void ExpressaoZ(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("=") ||
            tokenAtual.getToken().equals("<") ||
            tokenAtual.getToken().equals(">") ||
            tokenAtual.getToken().equals("<=") ||
            tokenAtual.getToken().equals(">=") ||
            tokenAtual.getToken().equals("<>") ||
            tokenAtual.getToken().equals("->")) {
            OpRelacional(tokens, semantico);
            transicao(tokens, semantico);
            ExpressaoSimples(tokens, semantico);
        }
    }



    public void ExpressaoSimples(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("+") ||
            tokenAtual.getToken().equals("-")) {
            Sinal(tokens, semantico);
            transicao(tokens, semantico);
            Termo(tokens, semantico);
            transicao(tokens, semantico);
            ExpressaoSimples2(tokens, semantico);
        }
        else {
            Termo(tokens, semantico);
            ExpressaoSimples2(tokens, semantico);
        }
    }

    public void ExpressaoSimples2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("+") ||
            tokenAtual.getToken().equals("-") ||
            tokenAtual.getToken().equals("or")) {
            OpAditivo(tokens, semantico);
            transicao(tokens, semantico);
            Termo(tokens, semantico);
            ExpressaoSimples2(tokens, semantico);
        }
    }



    public void Termo(ArrayList<Token> tokens, Semantico semantico) {
        Fator(tokens, semantico);
        transicao(tokens, semantico);
        Termo2(tokens, semantico);
    }

    public void Termo2(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("*") ||
            tokenAtual.getToken().equals("/") ||
            tokenAtual.getToken().equals("and")) {
            OpMultiplicativo(tokens, semantico);
            transicao(tokens, semantico);
            Fator(tokens, semantico);
            transicao(tokens, semantico);
            Termo2(tokens, semantico);
        }
    }



    public void Fator(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getClassificacao().equals("Identificador"))
            FatorExp(tokens, semantico);
        /*
        else if (tokenAtual.getClassificacao().equals("integer") ||
                 tokenAtual.getClassificacao().equals("real") ||
                 tokenAtual.getToken().equals("true") ||
                 tokenAtual.getToken().equals("false"))
            return;
        */
        else if (tokenAtual.getToken().equals("(")) {
            transicao(tokens, semantico);
            Expressao(tokens, semantico);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
        else if (tokenAtual.getToken().equals("not")) {
            transicao(tokens, semantico);
            Fator(tokens, semantico);
        }
        else
            exibirErro("Fator");
    }

    public void FatorExp(ArrayList<Token> tokens, Semantico semantico) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens, semantico);
            ListaExpressao(tokens, semantico);
            transicao(tokens, semantico);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
    }



    public void Sinal(ArrayList<Token> tokens, Semantico semantico) {
        if (!tokenAtual.getToken().equals("+") &&
            !tokenAtual.getToken().equals("-"))
            exibirErro("sinal");
    }



    public void OpRelacional(ArrayList<Token> tokens, Semantico semantico) {
        if (!tokenAtual.getToken().equals("=") &&
            !tokenAtual.getToken().equals("<") &&
            !tokenAtual.getToken().equals(">") &&
            !tokenAtual.getToken().equals("<=") &&
            !tokenAtual.getToken().equals(">=") &&
            !tokenAtual.getToken().equals("<>") &&
            !tokenAtual.getToken().equals("->"))
            exibirErro("relacional");
    }

    public void OpAditivo(ArrayList<Token> tokens, Semantico semantico) {
        if (!tokenAtual.getToken().equals("+") &&
            !tokenAtual.getToken().equals("-") &&
            !tokenAtual.getToken().equals("or"))
            exibirErro("aditivo");
    }

    public void OpMultiplicativo(ArrayList<Token> tokens, Semantico semantico) {
        if (!tokenAtual.getToken().equals("*") &&
            !tokenAtual.getToken().equals("/") &&
            !tokenAtual.getToken().equals("and"))
            exibirErro("multiplicativo");
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



    public static boolean isTipo(Token token) {
        return token.getToken().equals("integer") || token.getToken().equals("real") || token.getToken().equals("boolean");
    }



    public static ArrayList<Token> _tabela = new ArrayList<>();
    public static LeitorArquivo _leitorArquivo = new LeitorArquivo();
    public static Lexico _lexico = new Lexico();

    public static void main(String[] args) {
        _leitorArquivo.lerArquivo(_tabela); // Preenche lexico _tabela de símbolos
        _lexico.executar(_tabela); // Analisa lexico _tabela de símbolos

        Sintatico sintatico = new Sintatico();

        // Atribuindo tipos das variaveis

        String tipoEncontrado = "";
        for (int i = 0; i < _tabela.size(); i++) {
            System.out.println(_tabela.get(i)); // Printa tokens

            Token token = _tabela.get(i);

            // Se o tipoEncontrado estiver resetado, verifique se o token e' var
            if (tipoEncontrado.equals("")) {
                if (token.getToken().equals("var")) {
                    // Percorre novamente da posição atual até a palavra reservada depois de :
                    for (int j = i + 1; j < _tabela.size(); j++) {
                        //System.out.println("Simbolo '" + _tabela.get(j).getToken() + " e' igual a ':'?");

                        // Encontrou ':'
                        if (_tabela.get(j).getToken().equals(":") && (j + 1) < _tabela.size() && isTipo(_tabela.get(j + 1))) {
                            //System.out.println("Simbolo '" + _tabela.get(j).getToken() + "' encontrado com sucesso!");
                            tipoEncontrado = _tabela.get(j + 1).getToken();
                            break;
                        }
                    }
                }
            }
            else {
                // Se encontrou tipo
                if (token.getClassificacao().equals("Identificador")/* && !tipoEncontrado.equals("")*/) {
                    //System.out.println("Atribuido o tipo '" + tipoEncontrado + "' a variavel '" + token.getToken() + "'.");
                    token.setTipo(tipoEncontrado);
                }
                // Se encontrar ':', resete o tipoEncontrado
                if (token.getToken().equals(":")) {
                    //System.out.println("Restou tipoEncontrado!");
                    tipoEncontrado = "";
                }
            }
        }
    }
}
