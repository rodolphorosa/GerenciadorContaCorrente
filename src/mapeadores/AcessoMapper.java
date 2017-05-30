package mapeadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;

import excecoes.AcessoNaoEncontradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import modelos.Acesso;
import modelos.Conta;

public class AcessoMapper implements Mapeador<Acesso> {
	
	private static AcessoMapper instance;
	private static Connection conn;
	
	private AcessoMapper() {
		conn = ConnectionFactory.getInstance().getConnection();
	}

	@Override
	public Acesso obterPorId(Long id) 
			throws SQLException, 
			ClienteNaoEncontradoException, 
			ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Acesso obterMaisRecentePorConta(Conta conta) 
			throws SQLException, 
			AcessoNaoEncontradoException, 
			ContaNaoEncontradaException {
		Acesso acesso = null;
		
		String sql = "SELECT * FROM acesso a "
				   + "INNER JOIN conta c "
				   + "ON a.conta_id = c.id "
				   + "WHERE c.numero = ? "
				   + "ORDER BY a.data_logout, a.hora_logout DESC "
				   + "FETCH FIRST 1 ROWS ONLY";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, conta.getNumero());
		
		ResultSet resultado = stmt.executeQuery();
		
		if(!resultado.next()) {
			throw new AcessoNaoEncontradoException();
		} else {
			
			do {
				Date dLogin = resultado.getDate("data_login");
				Time hLogin = resultado.getTime("hora_login");
				Date dLogout = resultado.getDate("data_logout");
				Time hLogout = resultado.getTime("hora_logout");
				
				acesso = new Acesso(conta, dLogin, hLogin, dLogout, hLogout);
			} while(resultado.next());
			
		}
		
		return acesso;
	}

	@Override
	public void adicionar(Acesso acesso) throws SQLException {
		String sql = "INSERT INTO acesso (conta_id, data_login, hora_login, data_logout, hora_logout) "
				   + "VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setLong(1, acesso.getConta().getId());
		stmt.setDate(2, acesso.getDataLogin());
		stmt.setTime(3, acesso.getHoraLogin());
		stmt.setDate(4, acesso.getDataLogout());
		stmt.setTime(5, acesso.getHoraLogout());
		stmt.execute();
		stmt.close();		
	}

	@Override
	public void atualizar(Acesso acesso) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Acesso> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static AcessoMapper getInstance() {
		if (instance == null) {
			instance = new AcessoMapper();
		}
		return instance;
	}

}
