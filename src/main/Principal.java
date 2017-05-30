package main;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;

import excecoes.AcessoNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import excecoes.MovimentacaoInexistenteException;
import mapeadores.CreateDB;
import visoes.TelaLogin;

public class Principal {
	public static void main(String[] args) 
			throws ParseException, 
			SQLException, 
			ContaNaoEncontradaException, 
			MovimentacaoInexistenteException, 
			AcessoNaoEncontradoException {
		
		CreateDB.createDB();
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				TelaLogin tela = new TelaLogin();
				tela.setVisible(true);
			}
		
		});
	}
}