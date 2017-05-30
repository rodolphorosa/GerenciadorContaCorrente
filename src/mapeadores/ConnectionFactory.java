package mapeadores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static ConnectionFactory instance;	
	private static String url = "jdbc:derby:agenciaDB/agencia;create=true";
	
	private ConnectionFactory() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) System.out.println("Connected to database.");
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static ConnectionFactory getInstance() {
		if(instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

}
