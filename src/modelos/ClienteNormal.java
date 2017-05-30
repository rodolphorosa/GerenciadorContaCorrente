package modelos;

public final class ClienteNormal extends Cliente {

	public ClienteNormal(Long id, String nome, ContaNormal conta) {
		super(id, nome, conta);
	} 
	
	public String getNome() {
		return super.getNome();
	}
	
	public ContaNormal getConta() {
		return (ContaNormal) super.getConta();
	}
}