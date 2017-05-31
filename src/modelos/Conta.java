package modelos;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;

public abstract class Conta implements Runnable {
	
	private final Long id;
	private final String numero;
	private double saldo;
	
	/**
	 * @param id Identificador da conta.
	 * @param numero Numero da conta.
	 * @param saldo Saldo atual da conta.
	 * 
	 * */
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
	
	/**
	 * Atualiza o valor do saldo.
	 * 
	 * @param quantia Novo saldo.
	 * 
	 * */
	public void atualizarSaldo(double quantia) {
		saldo = quantia;
	}
	
	/**
	 * Adiciona um novo valor ao saldo atual.
	 * 
	 * @param quantia Valor a ser depositado.
	 * 
	 * */
	public void depositar(double quantia) {
		saldo = saldo + quantia;
	}
	
	/**
	 * Subtrai um valor ao saldo atual.
	 * 
	 * @param quantia Valor a ser debitado.
	 * 
	 * */
	public void debitarSaque(double quantia) throws SaldoInsuficienteException {
		saldo = saldo - quantia;
	}
	
	/**
	 * Substrai um valor somado a taxa de transferencia do saldo atual.
	 * 
	 * @param quantia Valor (mais taxa) a ser debitado.
	 * 
	 * */
	public void debitarTransferencia(double quantia) throws SaldoInsuficienteException, OperacaoInvalidaException {
		saldo = saldo - quantia;
	}
	
	/**
	 * Calcula o valor da taxa de transferencia.
	 * 
	 * @param quantia Valor da transferencia.
	 * 
	 * */
	public abstract double calcularTaxaTransferencia(double quantia);
	
	public String toString() {
		return String.format("Conta: { numero: %s, saldo: %.2f }", numero, saldo);
	}
}