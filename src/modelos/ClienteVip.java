package modelos;

public final class ClienteVip extends Cliente {

	public ClienteVip(Long id, String nome, ContaVip conta) {
		super(id, nome, conta);
	}
	
	public String getNome() {
		return super.getNome();
	}
	
	public ContaVip getConta() {
		return (ContaVip) super.getConta();
	}
}