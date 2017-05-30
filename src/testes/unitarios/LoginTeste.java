package testes.unitarios;

import org.junit.Test;

import excecoes.ContaInvalidaException;
import excecoes.SenhaInvalidaException;
import modelos.Login;

public class LoginTeste {
	
	@Test(expected = ContaInvalidaException.class)
	public void testeContaInvalida() throws ContaInvalidaException, SenhaInvalidaException {
		new Login("", "1111");
	}
	
	@Test(expected = SenhaInvalidaException.class)
	public void testeSenhaInvalida() throws ContaInvalidaException, SenhaInvalidaException {
		new Login("11111", "");
	}
}
