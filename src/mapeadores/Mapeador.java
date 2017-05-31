package mapeadores;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import excecoes.MovimentacaoInexistenteException;

public interface Mapeador<T> {
	
	public Object obterPorId(Long id) 
			throws SQLException, 
			ClienteNaoEncontradoException, 
			ContaNaoEncontradaException, 
			ParseException, 
			MovimentacaoInexistenteException;
	
	public void adicionar(T modelo) throws SQLException;
	
	public void atualizar(T modelo) throws SQLException;
	
	public Collection<T> obterTodos();

}
