import Lexico.Automato;
import Lexico.LeitorArquivo;
import Lexico.TabelaDeSimbolos;

public class Principal {

    public static void main(String[] args) {
        LeitorArquivo l = new LeitorArquivo();
        l.lerArquivo(); //Preenche a tabela de símbolos
        Automato a = new Automato();
        a.Analise(a);
        for(int i=0; i<TabelaDeSimbolos.tabela.size(); i++){
            System.out.println(TabelaDeSimbolos.tabela.get(i).toString());
        }
    }
}
