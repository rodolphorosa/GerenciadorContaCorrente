package mapeadores;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
	
	public static void createDB() {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		Statement st = null;
		
		String queryCreateTableConta = "CREATE TABLE conta "
				+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "numero VARCHAR(5) NOT NULL,"
				+ "saldo DOUBLE DEFAULT 0,"
				+ "tipo VARCHAR(30) NOT NULL,"
				+ "CONSTRAINT conta_unique_numero UNIQUE(numero),"
				+ "CONSTRAINT tipos_contas_permitidas CHECK (tipo = 'NORMAL' OR tipo = 'VIP'))";
		
		String queryCreateTableCliente = "CREATE TABLE cliente "
				+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "nome VARCHAR(30) NOT NULL, "
				+ "tipo VARCHAR(30) NOT NULL, "
				+ "CONSTRAINT tipos_clientes_permitidos CHECK (tipo = 'NORMAL' OR tipo = 'VIP'))";
		
		String queryCreateTableMovimentacao = "CREATE TABLE movimentacao "
				+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "conta_id INTEGER NOT NULL, "
				+ "data DATE NOT NULL, "
				+ "hora TIME NOT NULL, "
				+ "valor DOUBLE, "
				+ "tipo VARCHAR(20), "
				+ "CONSTRAINT conta_fk_movimentacao FOREIGN KEY (conta_id) REFERENCES conta (id), "
				+ "CONSTRAINT movimentacoes_permitidas CHECK "
				+ "(tipo = 'SAQUE' "
				+ "OR tipo = 'DEBITO' "
				+ "OR tipo = 'DEPOSITO' "
				+ "OR tipo = 'TRANSFERENCIA'))";
		
		String queryCreateTableAcesso = "CREATE TABLE acesso "
				+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "conta_id INTEGER NOT NULL ,"
				+ "data_login DATE NOT NULL, "
				+ "hora_login TIME NOT NULL, "
				+ "data_logout DATE NOT NULL, "
				+ "hora_logout TIME NOT NULL, "
				+ "CONSTRAINT conta_fk_acesso FOREIGN KEY (conta_id) REFERENCES conta (id))";
		
		String queryCreateTableLogin = "CREATE TABLE login "
				+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "cliente_id INTEGER NOT NULL, "
				+ "conta_id INTEGER NOT NULL, "
				+ "senha VARCHAR(4) NOT NULL, "
				+ "CONSTRAINT cliente_fk_login FOREIGN KEY (cliente_id) REFERENCES cliente (id), "
				+ "CONSTRAINT conta_fk_login FOREIGN KEY (conta_id) REFERENCES conta (id))";			
		
		String queriesClientes[] = {
				"INSERT INTO cliente (nome, tipo) VALUES ('João', 'NORMAL')", 
				"INSERT INTO cliente (nome, tipo) VALUES ('Helena', 'NORMAL')", 
				"INSERT INTO cliente (nome, tipo) VALUES ('Marcelo', 'NORMAL')", 
				"INSERT INTO cliente (nome, tipo) VALUES ('Maria', 'VIP')", 
				"INSERT INTO cliente (nome, tipo) VALUES ('André', 'VIP')", 
				"INSERT INTO cliente (nome, tipo) VALUES ('Luiza', 'VIP')"
		};
		
		String queriesContas[] = {
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('11111', 'NORMAL', 400.00)", 
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('22222', 'NORMAL', 850.00)", 
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('33333', 'NORMAL', 70.00)", 
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('44444', 'VIP', 1550.00)", 
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('55555', 'VIP', 4600.00)", 
				"INSERT INTO conta (numero, tipo, saldo) VALUES ('66666', 'VIP', 90.00)"
		};
		
		String queriesLogin[] = {
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('1111', 1, 1)", 
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('2222', 2, 2)", 
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('3333', 3, 3)", 
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('4444', 4, 4)", 
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('5555', 5, 5)", 
				"INSERT INTO login (senha, cliente_id, conta_id) VALUES ('6666', 6, 6)"
		};
		
		
		try {			
			
			st = conn.createStatement();
			st.execute(queryCreateTableCliente);
			st.execute(queryCreateTableConta);
			st.execute(queryCreateTableMovimentacao);
			st.execute(queryCreateTableAcesso);
			st.execute(queryCreateTableLogin);
			
			for(String q : queriesClientes) conn.prepareStatement(q).execute();
			for(String q : queriesContas) conn.prepareStatement(q).execute();
			for(String q : queriesLogin) conn.prepareStatement(q).execute();
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
			
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}