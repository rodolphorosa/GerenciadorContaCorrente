package excecoes;

@SuppressWarnings("serial")
public class MovimentacaoInexistenteException extends Exception {
	
	private static String mensagem = "N�o foram encontradas movimenta��es para esta conta.";
	
	public String toString() {
		return mensagem;
	}

	@Override
	public String getMessage() {
		return toString();
	}
}