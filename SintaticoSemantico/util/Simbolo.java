package SintaticoSemantico.util;

public class Simbolo
{
    String id;
    boolean terminal;

    public Simbolo(String id, boolean terminal)
    {
        this.id = id;
        this.terminal = terminal;
    }

    public Simbolo(String id)
    {
        this.id = id;
        this.terminal = false; // NaoTerminal, por padrão
    }
}
