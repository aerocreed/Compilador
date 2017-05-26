package Lexico;

public class Token {
    String token, classificacao;
    int linha;

    public Token(String token, String classificacao, int linha) {
        this.token = token;
        this.classificacao = classificacao;
        this.linha = linha;
    }

    public String getToken() { return token; }
    public String getClassificacao() { return classificacao; }
    public int getLinha() { return linha; }

    public void setToken(String token) { this.token = token; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }
    public void setLinha(int linha) { this.linha = linha; }

    @Override
    public String toString() {
        return "Token: " + token + "\tClasssificacao: " + classificacao + "\tLinha: " + linha;
    }
}
