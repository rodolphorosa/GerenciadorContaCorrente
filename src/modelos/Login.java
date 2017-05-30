package modelos;

import excecoes.ContaInvalidaException;
import excecoes.SenhaInvalidaException;
import uteis.ValidadorNumeroConta;
import uteis.ValidadorSenha;

public class Login {
	
	private String conta;
	private String senha;
	
	public Login(String conta, String senha) throws ContaInvalidaException, SenhaInvalidaException {
		this.conta = conta;
		this.senha = senha;
		
		validarLogin();
	}
	
	public String getConta() {
		return conta;
	}
	
	public String getSenha() {
		return senha;
	}
	
	private void validarLogin() 
			throws ContaInvalidaException, SenhaInvalidaException {
		
		if(!ValidadorNumeroConta.isNumeroContaValida(conta)) {
			throw new ContaInvalidaException();
		}
		
		if(!ValidadorSenha.isSenhaValida(senha)) {
			throw new SenhaInvalidaException();
		}
	}
}