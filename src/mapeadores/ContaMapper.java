package mapeadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import excecoes.ContaNaoEncontradaException;
import modelos.Conta;
import modelos.ContaNormal;
import modelos.ContaVip;
import modelos.TipoCliente;

public class ContaMapper implements Mapeador<Conta> {
	private static ContaMapper instance;
	private static Connection conn;
	
	private ContaMapper() {
		conn = ConnectionFactory.getInstance().getConnection();
	}

	@Override
	public Conta obterPorId(Long id) 
			throws SQLException, 
			ContaNaoEncontradaException, 
			ParseException {
		
		Conta conta = null;
		
		String sql = "SELECT * FROM conta WHERE id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setLong(1, id);
		
		ResultSet resultado = stmt.executeQuery();
		
		if(!resultado.next()) {
			
			throw new ContaNaoEncontradaException(); 
			
		} else {
			do {
				String numero = resultado.getString("numero");
				double saldo = resultado.getDouble("saldo");
				
				if (resultado.getString("tipo").equals(TipoCliente.NORMAL.toString())) {
					conta = new ContaNormal(id, numero, saldo);
				} else {
					conta = new ContaVip(id, numero, saldo);
				}
			} while(resultado.next());			
		}
		
		stmt.close();		
		return conta;
	}
	
	public Conta obterPorNumero(String numero) 
			throws SQLException, 
			ContaNaoEncontradaException, 
			ParseException {
		
		Conta conta = null;
		
		String sql = "SELECT id, saldo, tipo FROM conta WHERE numero = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, numero);
		
		ResultSet resultado = stmt.executeQuery();
		
		if(!resultado.next()) {
			
			throw new ContaNaoEncontradaException();
			
		} else {			
			do {				
				Long id = resultado.getLong("id");
				double saldo = resultado.getDouble("saldo");
				
				if (resultado.getString("tipo").equals(TipoCliente.NORMAL.toString())) {					
					conta = new ContaNormal(id, numero, saldo);
				} else {
					conta = new ContaVip(id, numero, saldo);
				}
			} while (resultado.next());
		}
		
		stmt.close();
		return conta;
	}

	@Override
	public void adicionar(Conta conta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(Conta conta) throws SQLException {
		
		String sql = "UPDATE conta SET saldo = ? WHERE id = ?";		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, conta.getSaldo());
		stmt.setLong(2, conta.getId());
		stmt.execute();
		stmt.close();
		
	}

	@Override
	public Collection<Conta> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ContaMapper getInstance() {
		if (instance == null) {
			instance = new ContaMapper();
		}
		return instance;
	}

}
