package SintaticoSemantico;

import Lexico.*;

import java.util.ArrayList;

public class Sintatico {
    public int index = 0;
    public Lexico lexico;
    public Token tokenAtual;



    public void executar(ArrayList<Token> tokens) { Programa(tokens); }

    // Executa o próximo token
    public void transicao(ArrayList<Token> tokens) {
        if (index < tokens.size()) {
            tokenAtual = tokens.get(index);
            // semantico.pushStack(token)
            index++;
        }
    }



    public void Programa(ArrayList<Token> tokens) {
        transicao(tokens);

        if (tokenAtual.getToken().equals("program")) {
            transicao(tokens);

            if (tokenAtual.getClassificacao().equals("Identificador")) {
                transicao(tokens);

                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens);
                    DeclaracoesVariaveis(tokens);
                    DeclaracoesSubProgramas(tokens);
                    ComandoComposto(tokens);
                    transicao(tokens);

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

    public void DeclaracoesVariaveis(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("var")) {
            transicao(tokens);

            if (tokenAtual.getClassificacao().equals("Identificador"))
                ListaDeclaracaoVariaveis(tokens);
            else
                exibirErro("Identificador");
        }
    }




    public void ListaDeclaracaoVariaveis(ArrayList<Token> tokens) {
        String varnome = tokenAtual.getToken();
        ListaIdentificadores(tokens);

        if (tokenAtual.getToken().equals(":")) {
            transicao(tokens);
            String vartipo = tokenAtual.getToken();
            int varlinha = tokenAtual.getLinha();

            // semantico.pushTypeStack(varnome, vartipo, varlinha)
            Tipo();

            transicao(tokens);

            if (tokenAtual.getToken().equals(";")) {
                transicao(tokens);
                ListaDeclaracaoVariaveis2(tokens);
            }
            else
                exibirErro(";");
        }
        else
            exibirErro(":");
    }

    private void ListaDeclaracaoVariaveis2(ArrayList<Token> tokens) {
        /*
            Como a função lista_declaracao_variaveis() chama a função next_token()
            antes de chamar lista_declaracao_variaveis_ percebi que estavamos olhando
            para os tokens errados comecei a verificar os tokens que estavam alcaçando
            a função lista_identificadores e percebi que estavam errados, quando alterei
            o teste para 1 token atrás a validação começou a funcionar, mas ainda não entendi
            o motivo.
         */
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            String varnome = tokenAtual.getToken();
            ListaIdentificadores(tokens);

            if (tokenAtual.getToken().equals(":")) {
                transicao(tokens);
                String vartipo = tokenAtual.getToken();
                int varlinha = tokenAtual.getLinha();

                // semantico.pushTypeStack(varnome, vartipo, varlinha)
                Tipo();

                transicao(tokens);

                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens);
                    ListaDeclaracaoVariaveis2(tokens);
                }
                else
                    exibirErro(";");
            }
            else
                exibirErro(":");
        }
    }



    public void ListaIdentificadores(ArrayList<Token> tokens) {
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens);
            ListaDeclaracaoVariaveis2(tokens);
        }
    }

    public void ListaIdentificadores2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals(",")) {
            transicao(tokens);

            if (tokenAtual.getClassificacao().equals("Identificador")) {
                // semantico.pushTypeStack(tokenAtual.getToken(), null, tokenAtual.getLinha())
                transicao(tokens);
                ListaIdentificadores2(tokens);
            }
            else
                exibirErro("Identificador");
        }
    }



    public void DeclaracoesSubProgramas(ArrayList<Token> tokens) {
        DeclaracoesSubProgramas2(tokens);
    }

    public void DeclaracoesSubProgramas2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("procedure")) {
            DeclaracaoSubPrograma(tokens);
            transicao(tokens);
            if (tokenAtual.getToken().equals(";")) {
                transicao(tokens);
                DeclaracoesSubProgramas2(tokens);
            }
            else
                exibirErro(";");
        }
    }



    public void DeclaracaoSubPrograma(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("procedure")) {
            transicao(tokens);
            if (tokenAtual.getClassificacao().equals("Identificador")) {
                transicao(tokens);
                Argumentos(tokens);
                if (tokenAtual.getToken().equals(";")) {
                    transicao(tokens);
                    DeclaracoesVariaveis(tokens);
                    DeclaracoesSubProgramas(tokens);
                    ComandoComposto(tokens);
                }
                else
                    exibirErro(";");
            }
            else
                exibirErro("Identificador");
        }
    }



    public void Argumentos(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens);
            ListaParametros(tokens);
            if (tokenAtual.getToken().equals(")")) {
                transicao(tokens);
                //return
            }
            else
                exibirErro(")");
        }
    }



    public void ListaParametros(ArrayList<Token> tokens) {
        String varnome = tokenAtual.getToken();
        ListaIdentificadores(tokens);
        if (tokenAtual.getToken().equals(":")) {
            transicao(tokens);
            String vartipo = tokenAtual.getToken();
            int varlinha = tokenAtual.getLinha();
            // semantico.pushTypeStack(varnome, vartipo, varlinha)
            Tipo();
            transicao(tokens);
            ListaParametros2(tokens);
        }
        else
            exibirErro(":");
    }

    public void ListaParametros2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals(";")) {
            transicao(tokens);
            String varnome = tokenAtual.getToken();
            ListaIdentificadores(tokens);
            if (tokenAtual.getToken().equals(":")) {
                transicao(tokens);
                String vartipo = tokenAtual.getToken();
                int varlinha = tokenAtual.getLinha();
                // semantico.pushTypeStack(varnome, vartipo, varlinha)
                Tipo();
                transicao(tokens);
                ListaParametros2(tokens);
            }
            // Faltou?
            //else
            //exibirErro(":");
        }
    }



    public void ComandoComposto(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("begin")) {
            transicao(tokens);
            ComandosOpcionais(tokens);
            if (!tokenAtual.getToken().equals("end"))
                exibirErro("end");
        }
        else
            exibirErro("begin");
    }

    public void ComandosOpcionais(ArrayList<Token> tokens) {
        if (tokenAtual.getClassificacao().equals("Identificador") ||
                tokenAtual.getToken().equals("while") ||
                tokenAtual.getToken().equals("if") ||
                tokenAtual.getToken().equals("begin"))
            ListaComandos(tokens);
    }



    public void ListaComandos(ArrayList<Token> tokens) {
        Comando(tokens);
        ListaComandos2(tokens);
    }

    public void ListaComandos2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals(";")) {
            transicao(tokens);
            Comando(tokens);
            ListaComandos2(tokens);
        }
    }



    public void Comando(ArrayList<Token> tokens) {
        // Identificadores
        if (tokenAtual.getClassificacao().equals("Identificador")) {
            transicao(tokens);

            if (tokenAtual.getToken().equals(":=")) {
                transicao(tokens);
                Expressao(tokens);
            } else if (tokenAtual.getToken().equals("(")) {
                AtivacaoProcedimento(tokens);
                transicao(tokens);
            }
        }
        // Comando composto
        else if (tokenAtual.getToken().equals("begin")) {
            ComandoComposto(tokens);
        }
        // -> if
        else if (tokenAtual.getToken().equals("if")) {
            transicao(tokens);
            Expressao(tokens);

            if (tokenAtual.getToken().equals("then")) {
                transicao(tokens);
                Comando(tokens);
                Else(tokens);
            }
            else
                exibirErro("then");
        }
        // -> while
        else if (tokenAtual.getToken().equals("while")) {
            transicao(tokens);
            Expressao(tokens);

            if (tokenAtual.getToken().equals("do")) {
                transicao(tokens);
                Comando(tokens);
            }
            else
                exibirErro("do");
        }
        // If it's not a command
        else
            exibirErro("Comando");
    }



    public void AtivacaoProcedimento(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens);
            ListaExpressao(tokens);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
    }



    public void Else(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("else")) {
            transicao(tokens);
            Comando(tokens);
        }
    }



    public void ListaExpressao(ArrayList<Token> tokens) {
        Expressao(tokens);

        if (!tokenAtual.getToken().equals(")"))
            ListaExpressao2(tokens);
    }

    public void ListaExpressao2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals(",")) {
            transicao(tokens);
            Expressao(tokens);
            ListaExpressao2(tokens);
        }
    }



    public void Expressao(ArrayList<Token> tokens) {
        ExpressaoSimples(tokens);
        ExpressaoZ(tokens);
    }



    public void ExpressaoZ(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("=") ||
                tokenAtual.getToken().equals("<") ||
                tokenAtual.getToken().equals(">") ||
                tokenAtual.getToken().equals("<=") ||
                tokenAtual.getToken().equals(">=") ||
                tokenAtual.getToken().equals("<>") ||
                tokenAtual.getToken().equals("->")) {
            OpRelacional(tokens);
            transicao(tokens);
            ExpressaoSimples(tokens);
        }
    }



    public void ExpressaoSimples(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("+") ||
                tokenAtual.getToken().equals("-")) {
            Sinal(tokens);
            transicao(tokens);
            Termo(tokens);
            transicao(tokens);
            ExpressaoSimples2(tokens);
        }
        else {
            Termo(tokens);
            ExpressaoSimples2(tokens);
        }
    }

    public void ExpressaoSimples2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("+") ||
                tokenAtual.getToken().equals("-") ||
                tokenAtual.getToken().equals("or")) {
            OpAditivo(tokens);
            transicao(tokens);
            Termo(tokens);
            ExpressaoSimples2(tokens);
        }
    }



    public void Termo(ArrayList<Token> tokens) {
        Fator(tokens);
        transicao(tokens);
        Termo2(tokens);
    }

    public void Termo2(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("*") ||
                tokenAtual.getToken().equals("/") ||
                tokenAtual.getToken().equals("and")) {
            OpMultiplicativo(tokens);
            transicao(tokens);
            Fator(tokens);
            transicao(tokens);
            Termo2(tokens);
        }
    }



    public void Fator(ArrayList<Token> tokens) {
        if (tokenAtual.getClassificacao().equals("Identificador"))
            FatorExp(tokens);
        /*
        else if (tokenAtual.getClassificacao().equals("integer") ||
                 tokenAtual.getClassificacao().equals("real") ||
                 tokenAtual.getToken().equals("true") ||
                 tokenAtual.getToken().equals("false"))
            return;
        */
        else if (tokenAtual.getToken().equals("(")) {
            transicao(tokens);
            Expressao(tokens);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
        else if (tokenAtual.getToken().equals("not")) {
            transicao(tokens);
            Fator(tokens);
        }
        else
            exibirErro("Fator");
    }

    public void FatorExp(ArrayList<Token> tokens) {
        if (tokenAtual.getToken().equals("(")) {
            transicao(tokens);
            ListaExpressao(tokens);
            transicao(tokens);

            if (!tokenAtual.getToken().equals(")"))
                exibirErro(")");
        }
    }



    public void Sinal(ArrayList<Token> tokens) {
        if (!tokenAtual.getToken().equals("+") &&
                !tokenAtual.getToken().equals("-"))
            exibirErro("sinal");
    }



    public void OpRelacional(ArrayList<Token> tokens) {
        if (!tokenAtual.getToken().equals("=") &&
                !tokenAtual.getToken().equals("<") &&
                !tokenAtual.getToken().equals(">") &&
                !tokenAtual.getToken().equals("<=") &&
                !tokenAtual.getToken().equals(">=") &&
                !tokenAtual.getToken().equals("<>") &&
                !tokenAtual.getToken().equals("->"))
            exibirErro("relacional");
    }

    public void OpAditivo(ArrayList<Token> tokens) {
        if (!tokenAtual.getToken().equals("+") &&
                !tokenAtual.getToken().equals("-") &&
                !tokenAtual.getToken().equals("or"))
            exibirErro("aditivo");
    }

    public void OpMultiplicativo(ArrayList<Token> tokens) {
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
    public static Sintatico _sintatico = new Sintatico();

    public static void main(String[] args) {
        _leitorArquivo.lerArquivo(_tabela); // Preenche lexico _tabela de símbolos
        _lexico.executar(_tabela); // Analisa lexico _tabela de símbolos

        Sintatico sintatico = new Sintatico();

        /*
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
                if (token.getClassificacao().equals("Identificador")) {
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
        */



        _sintatico.lexico = _lexico;
        _sintatico.executar(_tabela);
        //System.out.println("\n\n\n");
        //for (int i = 0; i < _tabela.size(); i++)
            //System.out.println(_tabela.get(i)); // Printa tokens
    }
}
