package excecoes;

@SuppressWarnings("serial")
public class ClienteNaoEncontradoException extends Exception {
	
	private static String mensagem = "Cliente não encontrado ou inexistente.";
	
	public String toString() {
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}

}
