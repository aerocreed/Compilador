package Lexico;

public class Simbolo {
    String token, classificacao;
    int linhaToken;

    public Simbolo(String token, String classificacao, int linhaToken) {
        this.token = token;
        this.classificacao = classificacao;
        this.linhaToken = linhaToken;
    }

    public String getToken() { return token; }
    public String getClassificacao() { return classificacao; }
    public int getLinhaToken() { return linhaToken; }

    public void setToken(String token) { this.token = token; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }
    public void setLinhaToken(int linhaToken) { this.linhaToken = linhaToken; }

    @Override
    public String toString() {
        return "Token: " + token + "\tClasssificacao: " + classificacao + "\tLinha: " + linhaToken;
    }
}
