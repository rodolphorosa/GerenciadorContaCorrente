package excecoes;

@SuppressWarnings("serial")
public class SenhaInvalidaException extends Exception {
	
	private static String mensagem = "Senha inv�lida.";
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}

}
