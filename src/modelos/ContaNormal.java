package modelos;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;

public final class ContaNormal extends Conta {
	
	public static final double TAXA_TRANSFERENCIA = 8.;

	/**
	 * @param id Identificador da conta.
	 * @param numero Numero da conta.
	 * @param saldo Saldo atual da conta.
	 * 
	 * */
	public ContaNormal(Long id, String numero, double saldo) {
		super(id, numero, saldo);
	}
	
	@Override
	public void depositar(double quantia) {
		super.depositar(quantia);
	}

	@Override
	public void debitarSaque(double quantia) throws SaldoInsuficienteException {
		if (quantia < this.getSaldo())
			super.debitarSaque(quantia);
		else
			throw new SaldoInsuficienteException();
	}
	
	@Override
	public void debitarTransferencia(double quantia) throws SaldoInsuficienteException, OperacaoInvalidaException {
		double debito = quantia + TAXA_TRANSFERENCIA;
		
		if(quantia > 1000.00) throw new OperacaoInvalidaException("Limite pra operação R$ 1000,00.");
		else if(debito > this.getSaldo()) throw new SaldoInsuficienteException();
		else super.debitarTransferencia(debito);
	}
	
	@Override
	public double calcularTaxaTransferencia(double quantia) {
		return TAXA_TRANSFERENCIA;		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
