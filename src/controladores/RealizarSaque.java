package controladores;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import excecoes.CampoObrigatorioException;
import excecoes.SaldoInsuficienteException;
import mapeadores.ContaMapper;
import mapeadores.MovimentacaoMapper;
import modelos.Conta;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class RealizarSaque {
	
	private MovimentacaoMapper mMapper;
	private ContaMapper contaMapper;
	private Conta conta;
	
	public RealizarSaque(Conta conta) {
		this.conta = conta;
		contaMapper = ContaMapper.getInstance();
		mMapper = MovimentacaoMapper.getInstance();
	}
	
	public void realizarOperacaoSaque(double quantia) 
			throws SaldoInsuficienteException, 
			SQLException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		conta.debitarSaque(quantia);
		contaMapper.atualizar(conta);
		
		Date data = new Date(Calendar.getInstance().getTimeInMillis());
		Time hora = new Time(Calendar.getInstance().getTimeInMillis());
		
		Movimentacao m = new Movimentacao(
					conta, 
					data, 
					hora, 
					-1 * quantia, 
					TipoMovimentacao.SAQUE);
		
		mMapper.adicionar(m);
	}
}
