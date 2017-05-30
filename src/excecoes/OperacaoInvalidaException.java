package excecoes;

@SuppressWarnings("serial")
public class OperacaoInvalidaException extends Exception {
	
	private String mensagem;
	
	public OperacaoInvalidaException(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public OperacaoInvalidaException() {
		this.mensagem = "Operação Inválida.";
	}
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return mensagem;
	}
}