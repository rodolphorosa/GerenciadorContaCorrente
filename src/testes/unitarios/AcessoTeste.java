package testes.unitarios;

import org.junit.Before;
import org.junit.Test;

import excecoes.AcessoException;
import excecoes.CampoObrigatorioException;
import modelos.Acesso;
import modelos.Conta;
import modelos.ContaVip;

public class AcessoTeste {
	private Conta conta;
	
	@Before
	public void criarAcesso() throws IllegalArgumentException, IllegalAccessException, AcessoException, CampoObrigatorioException {
		conta = new ContaVip((long) 1, "11111", 0);
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testeAtributoContaNulo() throws IllegalArgumentException, IllegalAccessException, AcessoException, CampoObrigatorioException {
		new Acesso(null);
	}
	
	@Test
	public void testeInicializarDataHoraLogout() throws AcessoException, IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {
		new Acesso(conta).iniciarDataHoraLogout();
	}
}