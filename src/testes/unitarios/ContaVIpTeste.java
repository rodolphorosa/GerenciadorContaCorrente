package testes.unitarios;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;
import modelos.ContaVip;

public class ContaVIpTeste {
	
	private ContaVip conta;
	
	@Before
	public void criarContaVip() {
		conta = new ContaVip((long) 1, "11111", 100.00);
	}
	
	@Test
	public void testeDebitarSaque() throws SaldoInsuficienteException {
		conta.debitarSaque(10);
		assertEquals("Saldo resultante inconsistente", 90.00, conta.getSaldo(), 0.0001);
	}
	
	@Test
	public void testeDebitarTransferencia() throws SaldoInsuficienteException, OperacaoInvalidaException {
		conta.debitarTransferencia(20);
		assertEquals("Saldo resultante inconsistente", 79.84, conta.getSaldo(), 0.0001);
	}
	
	@Test
	public void testeDebitarVisitaGerente() {
		conta.debitarVisitaGerente();
		assertEquals("Saldo resultante inconsistente", 50.00, conta.getSaldo(), 0.0001);
	}
	
	@Test
	public void testeCalcularTaxaTransferencia() {
		assertEquals("Taxa transferencia inconsistente", 0.4, conta.calcularTaxaTransferencia(50), 0.001);
	}
}
