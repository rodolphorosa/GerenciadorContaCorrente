package modelos;

import annotations.CampoObrigatorio;

public class Cliente {
	
	@CampoObrigatorio(obrigatorio = true)
	private final Long id;
	
	@CampoObrigatorio(obrigatorio = true)
	private final String nome;
	
	@CampoObrigatorio(obrigatorio = true)
	private final Conta conta;
	
	public Cliente(Long id, String nome, Conta conta) {
		this.id = id;
		this.nome = nome;
		this.conta = conta;
	}
	
	public String getNome() {
		return nome;		
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public String toString() {
		return String.format("%s: { %s }", nome, conta);
	}

}