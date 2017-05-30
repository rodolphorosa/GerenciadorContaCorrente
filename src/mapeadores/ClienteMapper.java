package mapeadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import excecoes.AcessoNaoEncontradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import modelos.Cliente;
import modelos.ClienteNormal;
import modelos.ClienteVip;
import modelos.ContaNormal;
import modelos.ContaVip;
import modelos.TipoCliente;

public class ClienteMapper implements Mapeador<Cliente> {
	
	private static ClienteMapper instance; 
	private static Connection conn;
	
	private ClienteMapper() {
		conn = ConnectionFactory.getInstance().getConnection();
	}

	@Override
	public Cliente obterPorId(Long id) 
			throws SQLException, 
			ClienteNaoEncontradoException, 
			ContaNaoEncontradaException, 
			AcessoNaoEncontradoException, 
			ParseException {
		
		Cliente cliente = null;
		
		String sql = "SELECT c.id AS cliente_id, l.conta_id AS conta_id, nome, tipo "
				   + "FROM cliente c "
				   + "INNER JOIN login l "
				   + "ON c.id = l.cliente_id "
				   + "WHERE c.id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, id);
		
		ResultSet resultado = stmt.executeQuery();
		
		if(!resultado.next()) {
			
			throw new ClienteNaoEncontradoException();
			
		} else {			
			do {
				Long idConta = resultado.getLong("conta_id");
				String nome = resultado.getString("nome");
				
				if (resultado.getString("tipo").equals(TipoCliente.NORMAL.toString())) {
					ContaNormal conta = (ContaNormal) ContaMapper.getInstance().obterPorId(idConta);				
					cliente = new ClienteNormal(id, nome, conta);
				} else {
					ContaVip conta = (ContaVip) ContaMapper.getInstance().obterPorId(idConta);
					cliente = new ClienteVip(id, nome, conta);
				}
			} while(resultado.next());
		}
		
		stmt.close();		
		return cliente;
	}

	@Override
	public void adicionar(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Cliente> obterTodos() {
		return null;
	}
	
	public static ClienteMapper getInstance() {
		if (instance == null) {
			instance = new ClienteMapper();
		}
		return instance;
	}

}
