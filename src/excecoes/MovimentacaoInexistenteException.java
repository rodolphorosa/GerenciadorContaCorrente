package excecoes;

@SuppressWarnings("serial")
public class MovimentacaoInexistenteException extends Exception {
	
	private static String mensagem = "Não foram encontradas movimentações para esta conta.";
	
	public String toString() {
		return mensagem;
	}

	@Override
	public String getMessage() {
		return toString();
	}
}