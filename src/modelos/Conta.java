package modelos;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;

public abstract class Conta implements Runnable {
	
	private final Long id;
	private final String numero;
	private double saldo;
	
	public Conta(Long id, String numero, double saldo) {
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public double getSaldo() {
		return saldo; 
	}
	
	public void atualizarSaldo(double quantia) {
		saldo = quantia;
	}
	
	public void depositar(double quantia) {
		saldo = saldo + quantia;
	}
	
	public void debitarSaque(double quantia) throws SaldoInsuficienteException {
		saldo = saldo - quantia;
	}
	
	public void debitarTransferencia(double quantia) throws SaldoInsuficienteException, OperacaoInvalidaException {
		saldo = saldo - quantia;
	}
	
	public abstract double calcularTaxaTransferencia(double quantia);
	
	public String toString() {
		return String.format("Conta: { numero: %s, saldo: %.2f }", numero, saldo);
	}
}