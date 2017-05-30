package excecoes;

@SuppressWarnings("serial")
public class OperacaoInvalidaException extends Exception {
	
	private String mensagem;
	
	public OperacaoInvalidaException(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public OperacaoInvalidaException() {
		this.mensagem = "Opera��o Inv�lida.";
	}
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return mensagem;
	}
}