package Lexico;

public class ElementoDaTabelaDeSimbolos {
	String token, classificacao;
        int linha_token;
	
	public ElementoDaTabelaDeSimbolos(String token, String classificacao, int linha_token) {
		this.token = token;
		this.classificacao = classificacao;
		this.linha_token = linha_token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

        public String getClassificacao() {
            return classificacao;
        }

        public void setClassificacao(String classificacao) {
            this.classificacao = classificacao;
        }

	public int getLinha_token() {
		return linha_token;
	}

	public void setLinha_token(int linha_token) {
		this.linha_token = linha_token;
	}
	
        @Override
        public String toString(){
            return "Token: " + token + "\tClasssificacao: " + classificacao + "\tLinha: " + linha_token;
            
        }
}
