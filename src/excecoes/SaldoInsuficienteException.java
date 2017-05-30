package excecoes;

@SuppressWarnings("serial")
public class SaldoInsuficienteException extends Exception {

	private static String mensagem = "Saldo insuficiente.";
	
	public String toString() {
		return mensagem;
	}
	
	public String getMessage() {
		return toString();
	}
}
