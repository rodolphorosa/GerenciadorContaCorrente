package controladores;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

import excecoes.CampoObrigatorioException;
import mapeadores.ContaMapper;
import mapeadores.MovimentacaoMapper;
import modelos.ContaVip;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class SolicitarVisitaGerente {

	private MovimentacaoMapper mMapper;
	private ContaMapper contaMapper;
	private ContaVip conta;
	
	/**
	 * @param conta Conta.
	 * 
	 * */
	public SolicitarVisitaGerente(ContaVip conta) {
		this.conta = conta;
		contaMapper = ContaMapper.getInstance();
		mMapper = MovimentacaoMapper.getInstance();
	}
	
	/**
	 * Debita a taxa da visita do cliente e registra na base de dados.
	 * 
	 * */
	public void realizarOperacaoSolicitarVisitaGerente() 
			throws SQLException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		conta.debitarVisitaGerente();
		contaMapper.atualizar(conta);
		
		Date data = new Date(Calendar.getInstance().getTimeInMillis());
		Time hora = new Time(Calendar.getInstance().getTimeInMillis());
		
		Movimentacao m = new Movimentacao(conta, data, hora, -1 * ContaVip.TAXA_VISITA_GERENTE, TipoMovimentacao.DEBITO);
		
		mMapper.adicionar(m);
	}
}