package excecoes;

@SuppressWarnings("serial")
public class AcessoException extends Exception {
	
	private String mensagem = "Data e hora inconsistentes";
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}