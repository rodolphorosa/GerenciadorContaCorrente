package testes.unitarios;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;
import modelos.ContaNormal;

public class ContaNormalTeste {
	
	private ContaNormal conta;
	
	@Before
	public void criarContaVip() {
		conta = new ContaNormal((long) 1, "11111", 100.00);
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void testeSaqueSaldoInsuficiente() throws SaldoInsuficienteException {
		conta.debitarSaque(200.00);
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void testeTransferenciaSaldoInsuficiente() throws SaldoInsuficienteException, OperacaoInvalidaException {
		conta.debitarTransferencia(200.00);
	}
	
	@Test(expected = OperacaoInvalidaException.class)
	public void testeTransferenciaOperacaoInvalida() throws SaldoInsuficienteException, OperacaoInvalidaException {
		conta.debitarTransferencia(2000.00);
	}
	
	@Test
	public void testeDebitarSaque() throws SaldoInsuficienteException {
		conta.debitarSaque(10);
		assertEquals("Saldo resultante inconsistente", 90.00, conta.getSaldo(), 0.0001);
	}
	
	@Test
	public void testeDebitarTransferencia() throws SaldoInsuficienteException, OperacaoInvalidaException {
		conta.debitarTransferencia(20);
		assertEquals("Saldo resultante inconsistente", 72.00, conta.getSaldo(), 0.0001);
	}
	
	@Test
	public void testeCalcularTaxaTransferencia() {
		assertEquals("Taxa transferencia inconsistente", 8.00, conta.calcularTaxaTransferencia(50), 0.001);
	}
}
