package SintaticoSemantico.util;

import java.util.LinkedList;

public class Teste
{
    public static void main(String[] args)
    {
        /*
            S -> aSa,
            S -> bSb,
            S -> £
         */

        LinkedList<Simbolo> simbolos = new LinkedList<>();
        LinkedList<Producao> producoes = new LinkedList<Producao>();

        simbolos.add(new Simbolo("a", true));
        simbolos.add(new Simbolo("S"));
        simbolos.add(new Simbolo("a", true));
        producoes.add(new Producao("S", (LinkedList<Simbolo>) simbolos.clone()));

        simbolos.add(new Simbolo("b", true));
        simbolos.add(new Simbolo("S"));
        simbolos.add(new Simbolo("b", true));
        producoes.add(new Producao("S", (LinkedList<Simbolo>) simbolos.clone()));

        simbolos.add(new Simbolo("", true));
        producoes.add(new Producao("S", (LinkedList<Simbolo>) simbolos.clone()));

        NaoTerminal simbolo = new NaoTerminal("S", producoes);

    }
}
