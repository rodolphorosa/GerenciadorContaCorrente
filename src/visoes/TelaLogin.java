package visoes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import excecoes.AcessoNaoEncontradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaInvalidaException;
import excecoes.ContaNaoEncontradaException;
import excecoes.SenhaInvalidaException;
import mapeadores.LoginMapper;
import modelos.Cliente;
import modelos.Login;

@SuppressWarnings("serial")
public class TelaLogin extends JFrame {
	private JTextField numeroContaField;
	private JPasswordField senhaField;
	private static Cliente clienteAtivo;
	private LoginMapper mapper;
	private Login login;
	
	public TelaLogin() {
		mapper = LoginMapper.getInstance();
		
		setResizable(false);
		setTitle("Gerenciador Conta Corrente - Log in");		
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel painel = new JPanel();
		painel.setBackground(new Color(255, 255, 255));
		
		setContentPane(painel);
		
		JLabel lblConta = new JLabel("Conta");
		lblConta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		numeroContaField = new JTextField();
		numeroContaField.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		senhaField = new JPasswordField();
		
		JButton btnLogIn = new JButton("ENTRAR");
		btnLogIn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
					try {
						login = new Login(numeroContaField.getText(), String.valueOf(senhaField.getPassword()));						
						clienteAtivo = mapper.obterClientePorLogin(login);
						iniciarTelaCliente(clienteAtivo);
						TelaLogin.this.dispose();
					} catch (SQLException e) {						
						e.printStackTrace();
					} catch (ClienteNaoEncontradoException e) {
						JOptionPane.showMessageDialog(TelaLogin.this, e);
					} catch (ContaInvalidaException e) {
						JOptionPane.showMessageDialog(TelaLogin.this, e);
					} catch (SenhaInvalidaException e) {
						JOptionPane.showMessageDialog(TelaLogin.this, e);
					} catch (ContaNaoEncontradaException e) {
						JOptionPane.showMessageDialog(TelaLogin.this, e);
					} catch (AcessoNaoEncontradoException e) {
						System.out.println(e);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		});
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_painel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_painel.createSequentialGroup()
									.addComponent(lblSenha)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(senhaField))
								.addGroup(gl_painel.createSequentialGroup()
									.addComponent(lblConta)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(numeroContaField, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(104)
							.addComponent(btnLogIn)))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_painel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConta)
						.addComponent(numeroContaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_painel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(senhaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnLogIn)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		painel.setLayout(gl_painel);
	}
	
	public void iniciarTelaCliente(Cliente cliente) {
		TelaCliente tela = new TelaCliente(cliente);
		tela.setVisible(true);		
	}
}
