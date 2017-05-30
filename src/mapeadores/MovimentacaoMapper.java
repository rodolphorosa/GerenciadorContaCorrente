package mapeadores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

import excecoes.MovimentacaoInexistenteException;
import modelos.Conta;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class MovimentacaoMapper implements Mapeador<Movimentacao> {
	
	private static MovimentacaoMapper instance;
	private static Connection conn;
	
	private MovimentacaoMapper() {
		conn = ConnectionFactory.getInstance().getConnection();
	}

	@Override
	public Movimentacao obterPorId(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adicionar(Movimentacao movimentacao) throws SQLException {
		String sql = "INSERT INTO movimentacao (conta_id, data, hora, valor, tipo)"
				   + "VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setLong(1, movimentacao.getConta().getId());
		stmt.setDate(2, movimentacao.getData());
		stmt.setTime(3, movimentacao.getHora());
		stmt.setDouble(4, movimentacao.getValor());
		stmt.setString(5, movimentacao.getTipo().toString());
		
		stmt.execute();
		stmt.close();		
	}
	
	public Collection<Movimentacao> obterPorConta(Conta conta) 
			throws SQLException, MovimentacaoInexistenteException {
		
		Collection<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
		
		String sql = "SELECT id, conta_id, data, hora, valor, tipo "
				   + "FROM movimentacao "
				   + "WHERE conta_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setLong(1, conta.getId());
		
		ResultSet resultados = stmt.executeQuery();
		
		if (!resultados.next()) {
			
			throw new MovimentacaoInexistenteException();
			
		} else {			
			do {				
				Long id = resultados.getLong("id");
				Date data = resultados.getDate("data");
				Time hora = resultados.getTime("hora");
				
				double valor = resultados.getDouble("valor");
				
				TipoMovimentacao tipo = TipoMovimentacao.valueOf(resultados.getString("tipo"));
				
				Movimentacao movimentacao = new Movimentacao(id, conta, data, hora, valor, tipo);				
				
				movimentacoes.add(movimentacao);
			} while (resultados.next());
		}
		
		stmt.close();		
		return movimentacoes;
	}
	
	public Movimentacao obterMaisRecentePorConta(Conta conta) throws SQLException, MovimentacaoInexistenteException {
		Movimentacao movimentacao;
		
		String sql = "SELECT id, conta_id, data, hora, valor, tipo "
				   + "FROM movimentacao "
				   + "WHERE conta_id = ? "
				   + "ORDER BY data, hora DESC "
				   + "FETCH FIRST 1 ROWS ONLY";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setLong(1, conta.getId());
		
		ResultSet resultados = stmt.executeQuery();
		
		if (!resultados.next()) {
			
			throw new MovimentacaoInexistenteException();
			
		} else {			
			do {				
				Long id = resultados.getLong("id");
				Date data = resultados.getDate("data");
				Time hora = resultados.getTime("hora");				
				double valor = resultados.getDouble("valor");				
				
				TipoMovimentacao tipo = TipoMovimentacao.valueOf(resultados.getString("tipo"));				
				movimentacao = new Movimentacao(id, conta, data, hora, valor, tipo);
			
			} while (resultados.next());
		}
		
		stmt.close();		
		return movimentacao;
	}

	@Override
	public void atualizar(Movimentacao movimentacao) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Movimentacao> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static MovimentacaoMapper getInstance() {
		if (instance == null) {
			instance = new MovimentacaoMapper();
		}
		return instance;
	}

}
