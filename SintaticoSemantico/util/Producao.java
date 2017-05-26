package SintaticoSemantico.util;

import java.util.LinkedList;

public final class Producao extends Simbolo
{
    LinkedList<Simbolo> simbolos;

    public Producao(String id, LinkedList<Simbolo> simbolos)
    {
        super(id, false);
        this.simbolos = simbolos;
    }
}
