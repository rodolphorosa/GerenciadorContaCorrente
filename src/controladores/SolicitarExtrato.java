package controladores;

import java.sql.SQLException;
import java.util.Collection;

import excecoes.MovimentacaoInexistenteException;
import mapeadores.MovimentacaoMapper;
import modelos.Conta;
import modelos.Movimentacao;

public class SolicitarExtrato {
	
	private MovimentacaoMapper mMapper;
	private Conta conta;
	
	/**
	 * @param conta Conta.
	 * 
	 * */
	public SolicitarExtrato(Conta conta) {
		this.conta = conta;
		mMapper = MovimentacaoMapper.getInstance();
	}
	
	/**
	 * Solitica as movimentacoes na conta do cliente.
	 * 
	 * @param 
	 * @return rows Tabela com todas as movimentacoes da conta.
	 * 
	 * */
	public String[][] realizarSolitacaoExtrato() throws SQLException, MovimentacaoInexistenteException {		
		Collection<Movimentacao> movimentacoes = mMapper.obterPorConta(conta);
		
		String rows[][] = new String[movimentacoes.size()][4];		
		
		int i = 0;
		for(Movimentacao m : movimentacoes) {
			rows[i] = m.toString().split("\t");
			++i;
		}
		
		return rows;
	}
}