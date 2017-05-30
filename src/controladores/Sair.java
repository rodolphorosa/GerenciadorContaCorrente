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
	
	public Sair(Conta conta) {
		this.conta = conta;
		contaMapper = ContaMapper.getInstance();
		acessoMapper = AcessoMapper.getInstance();
	}
	
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