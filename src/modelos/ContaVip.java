package modelos;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;

public final class ContaVip extends Conta {	
	
	public static final double TAXA_TRANSFERENCIA = .008;
	public static final double TAXA_VISITA_GERENTE = 50.;
	public static final double TAXA_SALDO_NEGATIVO = .001;

	public ContaVip(Long id, String numero, double saldo) {
		super(id, numero, saldo);
	}
	
	@Override
	public void depositar(double quantia) {
		super.depositar(quantia);
	}

	@Override
	public void debitarSaque(double quantia) throws SaldoInsuficienteException {
		super.debitarSaque(quantia);		
	}

	@Override
	public void debitarTransferencia(double quantia) throws SaldoInsuficienteException, OperacaoInvalidaException {
		double debito = quantia + calcularTaxaTransferencia(quantia);
		super.debitarTransferencia(debito);
	}
	
	public void debitarVisitaGerente() {
		super.atualizarSaldo(this.getSaldo() - TAXA_VISITA_GERENTE);
	}
	
	@Override
	public double calcularTaxaTransferencia(double quantia) {
		return quantia * TAXA_TRANSFERENCIA;
	}
	
	public void calcularSaldoNegativo(Date data, Time hora) throws ParseException {		
		java.util.Date hoje = new java.util.Date(Calendar.getInstance().getTimeInMillis());
		java.util.Date ultimoAcesso = null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		String str = String.format("%s %s", data, hora);		
		ultimoAcesso = df.parse(str);
		
		long intervalo = TimeUnit.MILLISECONDS.toMinutes(hoje.getTime() - ultimoAcesso.getTime());
		
		double taxa = this.getSaldo() * TAXA_SALDO_NEGATIVO * intervalo;
		
		super.atualizarSaldo(this.getSaldo() + taxa);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(60000);
				if (ContaVip.this.getSaldo() < 0) {
					double novoSaldo = ContaVip.this.getSaldo() 
										+ (ContaVip.this.getSaldo() 
												* TAXA_SALDO_NEGATIVO);
					ContaVip.this.atualizarSaldo(novoSaldo);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}