package excecoes;

@SuppressWarnings("serial")
public class CampoObrigatorioException extends Exception {
	
	private String mensagem;
	
	public CampoObrigatorioException(String campo) {
		mensagem = String.format("O atributo %s é obrigatório.", campo);
	}
	
	public String toString() {		
		return mensagem;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}
