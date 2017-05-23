package SintaticoSemantico;

/**
 * Created by River on 20/05/2017.
 */
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
