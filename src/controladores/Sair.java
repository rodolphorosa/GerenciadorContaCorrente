package controladores;

import java.sql.SQLException;

import excecoes.AcessoException;
import excecoes.CampoObrigatorioException;
import mapeadores.AcessoMapper;
import mapeadores.ContaMapper;
import modelos.Acesso;
import modelos.Conta;

public class Sair {
	
	private AcessoMapper acessoMapper;
	private ContaMapper contaMapper;
	private Conta conta;
	
	/**
	 * @param conta Conta.
	 * 
	 * */
	public Sair(Conta conta) {
		this.conta = conta;
		contaMapper = ContaMapper.getInstance();
		acessoMapper = AcessoMapper.getInstance();
	}
	
	/**
	 * Atualiza os dados da conta do cliente antes de finalizar a aplicacao.
	 * 
	 * Os dados do logout (data e hora) sao inicializados e registrados na base de dados.
	 * 
	 * @param acesso Acesso do cliente.
	 * 
	 * */
	public void finalizarAplicacao(Acesso acesso) 
			throws SQLException, 
			AcessoException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		contaMapper.atualizar(conta);
		acesso.iniciarDataHoraLogout();
		acessoMapper.adicionar(acesso);
	}
}