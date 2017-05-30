package excecoes;

@SuppressWarnings("serial")
public class ContaInvalidaException extends Exception {
	
	private static String mensagem = "Conta inválida";

	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}
