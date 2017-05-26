package SintaticoSemantico.util;

import java.util.LinkedList;

public final class NaoTerminal extends Simbolo
{
    LinkedList<Simbolo> filhos = new LinkedList<>();
    LinkedList<Producao> producoes;

    public NaoTerminal(String id, boolean terminal, LinkedList<Producao> producoes)
    {
        super(id, terminal);
        this.producoes = producoes;
    }

    public NaoTerminal(String id, LinkedList<Producao> producoes)
    {
        super(id);
        this.filhos = filhos;
        this.producoes = producoes;
    }

    /*public void gerarFilhos()
    {
        int idProducao = -1;
        for (int i = 0; i < producoes.size(); i++)
            if (id == producoes.get(i).id) // Esse objeto não-terminal tem regra nas produções (ex, 'S -> aSa')
                idProducao = i;
        if (idProducao == -1) // Não há mais como gerar mais produções porque já chegou nas folhas
            return;

        // Ex, com a regra 'S -> aSa', os símbolos 'aSa' se tornarão exatamente os novos 'filhos' do objeto não-terminal
        filhos = producoes.get(idProducao).simbolos;

        // Agora que temos os 'filhos', devemos executar o mesmo método nos filhos não-terminais
        for (Token filho : filhos)
            if (filho instanceof NaoTerminal && !filho.terminal)
                ((NaoTerminal)filho).gerarFilhos();
    }*/
}
