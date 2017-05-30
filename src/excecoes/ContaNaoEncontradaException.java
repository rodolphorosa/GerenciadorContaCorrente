package excecoes;

@SuppressWarnings("serial")
public class ContaNaoEncontradaException extends Exception {
	
	private static String mensagem = "Conta n�o encontrada ou inexistente.";

	public String toString() {
		return mensagem;		
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}
