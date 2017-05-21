import java.util.LinkedList;

/**
 * Created by River on 20/05/2017.
 */
public final class Producao extends Simbolo
{
    LinkedList<Simbolo> simbolos;

    public Producao(String id, LinkedList<Simbolo> simbolos)
    {
        super(id, false);
        this.simbolos = simbolos;
    }
}
