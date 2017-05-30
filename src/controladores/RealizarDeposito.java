package controladores;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import excecoes.CampoObrigatorioException;
import mapeadores.ContaMapper;
import mapeadores.MovimentacaoMapper;
import modelos.Conta;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class RealizarDeposito {
	
	private MovimentacaoMapper mMapper;
	private ContaMapper contaMapper;
	private Conta conta;
	
	public RealizarDeposito(Conta conta) {
		this.conta = conta;
		contaMapper = ContaMapper.getInstance();
		mMapper = MovimentacaoMapper.getInstance();
	}
	
	public void realizarOperacaoDeposito(double quantia) 
			throws SQLException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		conta.depositar(quantia);
		contaMapper.atualizar(conta);
		
		Date data = new Date(Calendar.getInstance().getTimeInMillis());
		Time hora = new Time(Calendar.getInstance().getTimeInMillis());
		
		Movimentacao m = new Movimentacao(
					conta, 
					data, 
					hora, 
					quantia, 
					TipoMovimentacao.DEPOSITO);
		
		mMapper.adicionar(m);
	}
}