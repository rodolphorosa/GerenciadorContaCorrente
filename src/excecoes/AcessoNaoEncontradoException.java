package excecoes;

@SuppressWarnings("serial")
public class AcessoNaoEncontradoException extends Exception {

	private String mensagem = "Acesso n�o encontrado.";
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}
