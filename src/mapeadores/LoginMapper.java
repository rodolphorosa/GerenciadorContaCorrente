package mapeadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import excecoes.AcessoNaoEncontradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaInvalidaException;
import excecoes.ContaNaoEncontradaException;
import excecoes.SenhaInvalidaException;
import modelos.Cliente;
import modelos.Login;

public class LoginMapper {	
	private static LoginMapper instance;
	private static Connection conn;
	
	private LoginMapper() {
		conn = ConnectionFactory.getInstance().getConnection();
	}
	
	public Cliente obterClientePorLogin(Login login) 
			throws SQLException, 
			ClienteNaoEncontradoException, 
			ContaInvalidaException, 
			SenhaInvalidaException, 
			ContaNaoEncontradaException, 
			AcessoNaoEncontradoException, 
			ParseException {
		
		Cliente cliente = null;
		
		String sql = "SELECT cliente_id "
				   + "FROM login l "
				   + "INNER JOIN conta c "
				   + "ON c.id = l.conta_id "
				   + "WHERE c.numero = ? AND l.senha = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, login.getConta());
		stmt.setString(2, login.getSenha());
		
		ResultSet resultado = stmt.executeQuery();
		
		if (!resultado.next()) {

			throw new ClienteNaoEncontradoException();

		} else {			
			do {
				Long id = resultado.getLong("cliente_id");
				cliente = ClienteMapper.getInstance().obterPorId(id);
			} while(resultado.next());
		}
		
		stmt.close();
		return cliente;		
	}	
	
	public static LoginMapper getInstance() {
		if (instance == null) {
			instance = new LoginMapper();
		}
		return instance;
	}
}