package Lexico;

public class Token {
    String token;
    String classificacao = "";
    String tipo = "";
    int linha;

    public Token(String token, int linha) {
        this.token = token;
        this.linha = linha;
    }    
    
    public Token(String token, String classificacao, int linha) {
        this.token = token;
        this.classificacao = classificacao;
        this.linha = linha;
    }    
    
    public Token(String token, String classificacao, String tipo, int linha) {
        this.token = token;
        this.classificacao = classificacao;
        this.tipo = tipo;
        this.linha = linha;
    }

    public String getToken() { return token; }
    public String getClassificacao() { return classificacao; }
    public String getTipo() { return tipo; }
    public int getLinha() { return linha; }

    public void setToken(String token) { this.token = token; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setLinha(int linha) { this.linha = linha; }

    @Override
    public String toString() {
        return "Token: " + token + "\tClasssificacao: " + classificacao + "\tLinha: " + linha;
    }
}
