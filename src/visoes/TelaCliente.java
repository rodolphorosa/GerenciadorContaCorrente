package visoes;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import excecoes.AcessoException;
import excecoes.CampoObrigatorioException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import excecoes.MovimentacaoInexistenteException;
import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;
import modelos.Acesso;
import modelos.Cliente;
import modelos.ClienteNormal;
import modelos.ContaVip;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controladores.RealizarDeposito;
import controladores.RealizarSaque;
import controladores.RealizarTransferencia;
import controladores.Sair;
import controladores.SolicitarExtrato;
import controladores.SolicitarVisitaGerente;

@SuppressWarnings("serial")
public class TelaCliente extends JFrame {
	private Thread thread;
	private Acesso acesso;
	
	private String colunasExtrato[]= { "Data", "Hora", "DESCRIÇÃO", "VALOR" };
	
	private Sair controladorSair;
	private RealizarSaque controladorSaque;
	private SolicitarExtrato controladorExtrato;
	private RealizarDeposito controladorDeposito;
	private RealizarTransferencia controladorTransferencia;
	private SolicitarVisitaGerente controladorVisitaGerente;
	
	private CardLayout cardLayout = new CardLayout();
	private JPanel painelInicial,
	               painelSaque, 
	               painelSaldo, 
	               painelExtrato, 
	               painelDeposito,
	               painelTransferencia;
	private JPanel cardPanel;	
	private JTextField textFieldContaCorrente;
	private JFormattedTextField textFieldValorSaque;
	private JFormattedTextField textFieldValorDeposito;
	private JFormattedTextField textFieldValorTransferencia;
	
	private NumberFormat nf = NumberFormat.getCurrencyInstance();
	private JTable tableExtrato;
	
	public TelaCliente(Cliente cliente) {
		try {
			acesso = new Acesso(cliente.getConta());
		} catch (Throwable e) {
			System.out.println(e);
		}
		
		thread = new Thread(cliente.getConta());
		thread.start();
		
		controladorSair = new Sair(cliente.getConta());
		controladorSaque = new RealizarSaque(cliente.getConta());
		controladorExtrato = new SolicitarExtrato(cliente.getConta());
		controladorDeposito = new RealizarDeposito(cliente.getConta());		
		controladorTransferencia = new RealizarTransferencia(cliente.getConta());
		if(cliente.getConta() instanceof ContaVip) {
			controladorVisitaGerente = new SolicitarVisitaGerente((ContaVip) cliente.getConta());
		}
		
		setResizable(false);
		setTitle("Gerenciador Conta Corrente");		
		setSize(480, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		cardPanel = new JPanel();
		
		setContentPane(cardPanel);
		
		cardPanel.setLayout(cardLayout);
		painelInicial = new JPanel();
		painelInicial.setBackground(Color.WHITE);
		painelSaque = new JPanel();
		painelSaque.setBackground(Color.WHITE);
		painelSaldo = new JPanel();
		painelSaldo.setBackground(Color.WHITE);
		painelExtrato = new JPanel();
		painelExtrato.setBackground(Color.WHITE);
		painelDeposito = new JPanel();
		painelDeposito.setBackground(Color.WHITE);
		painelTransferencia = new JPanel();
		painelTransferencia.setBackground(Color.WHITE);
		
		cardPanel.add(painelInicial, "Tela Inicial");	
		
		JTextArea txtBoasVindas = new JTextArea();
		txtBoasVindas.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtBoasVindas.setEditable(false);
		txtBoasVindas.setText(String.format("Seja bem-vindo(a), %s", cliente.getNome()));
		GroupLayout gl_painelInicial = new GroupLayout(painelInicial);
		gl_painelInicial.setHorizontalGroup(
			gl_painelInicial.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelInicial.createSequentialGroup()
					.addGap(123)
					.addComponent(txtBoasVindas, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(123, Short.MAX_VALUE))
		);
		gl_painelInicial.setVerticalGroup(
			gl_painelInicial.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelInicial.createSequentialGroup()
					.addGap(126)
					.addComponent(txtBoasVindas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(125, Short.MAX_VALUE))
		);
		painelInicial.setLayout(gl_painelInicial);
		
		cardPanel.add(painelSaldo, "Tela Saldo");
		
		JTextArea txtrSaldoDisponivel = new JTextArea();
		txtrSaldoDisponivel.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtrSaldoDisponivel.setEditable(false);
		txtrSaldoDisponivel.setColumns(20);
		GroupLayout gl_painelSaldo = new GroupLayout(painelSaldo);
		gl_painelSaldo.setHorizontalGroup(
			gl_painelSaldo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSaldo.createSequentialGroup()
					.addGap(132)
					.addComponent(txtrSaldoDisponivel, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(131, Short.MAX_VALUE))
		);
		gl_painelSaldo.setVerticalGroup(
			gl_painelSaldo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSaldo.createSequentialGroup()
					.addGap(123)
					.addComponent(txtrSaldoDisponivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(123, Short.MAX_VALUE))
		);
		painelSaldo.setLayout(gl_painelSaldo);		
		
		cardPanel.add(painelSaque, "Tela Saque");
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		textFieldValorSaque = new JFormattedTextField(nf);
		textFieldValorSaque.setFont(new Font("Consolas", Font.PLAIN, 12));
		textFieldValorSaque.setColumns(10);
		textFieldValorSaque.setValue(new Double(0));
		
		JButton btnSacar = new JButton("SACAR");
		btnSacar.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		btnSacar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				double quantia = ((Number) textFieldValorSaque.getValue()).doubleValue();
				
				try {
					
					if(quantia <= 0){
						throw new OperacaoInvalidaException("Valor inválido ou não informado.");
					}
					
					controladorSaque.realizarOperacaoSaque(quantia);
					JOptionPane.showMessageDialog(TelaCliente.this, "Operação realizada com sucesso!");
					cardLayout.show(cardPanel, "Tela Inicial");
				
				} catch (SaldoInsuficienteException e) {					
					JOptionPane.showMessageDialog(TelaCliente.this, e);					
				} catch (OperacaoInvalidaException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (CampoObrigatorioException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalAccessException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				}
			}
		});
		
		GroupLayout gl_painelSaque = new GroupLayout(painelSaque);
		gl_painelSaque.setHorizontalGroup(
			gl_painelSaque.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSaque.createSequentialGroup()
					.addGap(135)
					.addComponent(lblValor)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldValorSaque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSacar)
					.addContainerGap(134, Short.MAX_VALUE))
		);
		gl_painelSaque.setVerticalGroup(
			gl_painelSaque.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelSaque.createSequentialGroup()
					.addGap(121)
					.addGroup(gl_painelSaque.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor)
						.addComponent(textFieldValorSaque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSacar))
					.addContainerGap(126, Short.MAX_VALUE))
		);
		painelSaque.setLayout(gl_painelSaque);
		cardPanel.add(painelExtrato, "Tela Extrato");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_painelExtrato = new GroupLayout(painelExtrato);
		gl_painelExtrato.setHorizontalGroup(
			gl_painelExtrato.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE)
		);
		gl_painelExtrato.setVerticalGroup(
			gl_painelExtrato.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
		);		
		
		painelExtrato.setLayout(gl_painelExtrato);
		cardPanel.add(painelDeposito, "Tela Deposito");
		
		JLabel lblValor_1 = new JLabel("Valor:");
		lblValor_1.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		textFieldValorDeposito = new JFormattedTextField(nf);
		textFieldValorDeposito.setFont(new Font("Consolas", Font.PLAIN, 12));
		textFieldValorDeposito.setColumns(10);
		textFieldValorDeposito.setValue(new Double(0));
		
		JButton btnDepositar = new JButton("DEPOSITAR");
		btnDepositar.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		btnDepositar.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent event) {
				double quantia = ((Number) textFieldValorDeposito.getValue()).doubleValue();
				
				try {
					
					if(quantia <= 0){
						throw new OperacaoInvalidaException("Valor inválido ou não informado.");
					}
					
					controladorDeposito.realizarOperacaoDeposito(quantia);
					JOptionPane.showMessageDialog(TelaCliente.this, "Operação realizada com sucesso!");
					cardLayout.show(cardPanel, "Tela Inicial");
				
				} catch (OperacaoInvalidaException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (CampoObrigatorioException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalAccessException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				}
			}
			
		});
		
		GroupLayout gl_painelDeposito = new GroupLayout(painelDeposito);
		gl_painelDeposito.setHorizontalGroup(
			gl_painelDeposito.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDeposito.createSequentialGroup()
					.addGap(121)
					.addComponent(lblValor_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textFieldValorDeposito, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDepositar)
					.addContainerGap(120, Short.MAX_VALUE))
		);
		gl_painelDeposito.setVerticalGroup(
			gl_painelDeposito.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDeposito.createSequentialGroup()
					.addGap(121)
					.addGroup(gl_painelDeposito.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor_1)
						.addComponent(textFieldValorDeposito, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDepositar))
					.addContainerGap(126, Short.MAX_VALUE))
		);
		painelDeposito.setLayout(gl_painelDeposito);
		cardPanel.add(painelTransferencia, "Tela Transferencia");
		
		JLabel lblContaCorrente = new JLabel("Conta Corrente:");
		lblContaCorrente.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		textFieldContaCorrente = new JTextField();
		textFieldContaCorrente.setFont(new Font("Consolas", Font.PLAIN, 12));
		textFieldContaCorrente.setColumns(10);
		
		JLabel lblValor_2 = new JLabel("Valor:");
		lblValor_2.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		textFieldValorTransferencia = new JFormattedTextField(nf);
		textFieldValorTransferencia.setFont(new Font("Consolas", Font.PLAIN, 12));
		textFieldValorTransferencia.setColumns(10);
		textFieldValorTransferencia.setValue(new Double(0));
		
		JButton btnTransferir = new JButton("TRANSFERIR");
		btnTransferir.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		btnTransferir.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent event) {
				double quantia = ((Number) textFieldValorTransferencia.getValue()).doubleValue();
				String numeroContaDestinatario = textFieldContaCorrente.getText();
				
				try {
					
					if(quantia <= 0){
						throw new OperacaoInvalidaException("Valor inválido ou não informado.");
					}
					
					controladorTransferencia.realizarOperacaoTransferencia(numeroContaDestinatario, quantia);
					JOptionPane.showMessageDialog(TelaCliente.this, "Operação realizada com sucesso!");
					cardLayout.show(cardPanel, "Tela Inicial");
				
				} catch (SQLException e) {
					System.out.println(e);
				} catch (ClienteNaoEncontradoException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (ContaNaoEncontradaException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (OperacaoInvalidaException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (SaldoInsuficienteException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
				} catch (CampoObrigatorioException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} catch (IllegalAccessException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					cardLayout.show(cardPanel, "Tela Inicial");
				} 
			}
		});
		
		GroupLayout gl_painelTransferencia = new GroupLayout(painelTransferencia);
		gl_painelTransferencia.setHorizontalGroup(
			gl_painelTransferencia.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTransferencia.createSequentialGroup()
					.addGroup(gl_painelTransferencia.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painelTransferencia.createSequentialGroup()
							.addGap(142)
							.addGroup(gl_painelTransferencia.createParallelGroup(Alignment.LEADING)
								.addComponent(lblContaCorrente)
								.addComponent(lblValor_2))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_painelTransferencia.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldValorTransferencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldContaCorrente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_painelTransferencia.createSequentialGroup()
							.addGap(186)
							.addComponent(btnTransferir)))
					.addContainerGap(141, Short.MAX_VALUE))
		);
		gl_painelTransferencia.setVerticalGroup(
			gl_painelTransferencia.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelTransferencia.createSequentialGroup()
					.addGap(80)
					.addGroup(gl_painelTransferencia.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContaCorrente)
						.addComponent(textFieldContaCorrente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_painelTransferencia.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblValor_2)
						.addComponent(textFieldValorTransferencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnTransferir)
					.addContainerGap(96, Short.MAX_VALUE))
		);
		painelTransferencia.setLayout(gl_painelTransferencia);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(245, 245, 245));
		setJMenuBar(menuBar);
		
		JMenu mnOpcoes = new JMenu("Opcoes");
		menuBar.add(mnOpcoes);
		
		JMenuItem mntmSaque = new JMenuItem("Saque");
		mnOpcoes.add(mntmSaque);
		
		JMenuItem mntmSaldo = new JMenuItem("Saldo");
		mnOpcoes.add(mntmSaldo);
		
		JMenuItem mntmExtrato = new JMenuItem("Extrato");
		mnOpcoes.add(mntmExtrato);
		
		JMenuItem mntmDeposito = new JMenuItem("Dep\u00F3sito");
		mnOpcoes.add(mntmDeposito);
		
		JMenuItem mntmTransferencia = new JMenuItem("Transfer\u00EAncia");
		mnOpcoes.add(mntmTransferencia);
		
		JMenuItem mntmSolicitarGerente = new JMenuItem("Solicitar Gerente");
		mnOpcoes.add(mntmSolicitarGerente);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnOpcoes.add(mntmSair);		
		
		
		if(cliente instanceof ClienteNormal) {
			mntmSolicitarGerente.setEnabled(false);
		}
		
		mntmSaque.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {				
				cardLayout.show(cardPanel, "Tela Saque");
			}
			
		});
		
		mntmSaldo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				double saldo = cliente.getConta().getSaldo();
				String strSaldo = saldo >= 0 ? nf.format(saldo) : String.format("(%s)", nf.format(saldo));
				txtrSaldoDisponivel.setText(String.format("Saldo Disponível: %s", strSaldo));
				cardLayout.show(cardPanel, "Tela Saldo");
			}
		});
		
		mntmExtrato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String[][] extrato;
				
				DefaultTableModel model = new DefaultTableModel(colunasExtrato, 0) {
					
					public boolean isCellEditable(int row, int column) {
						return false;
					}
					
				};
				
				tableExtrato = new JTable();
				tableExtrato.setFont(new Font("Consolas", Font.PLAIN, 12));
				tableExtrato.setModel(model);
				
				scrollPane.setViewportView(tableExtrato);
				
				try {
					
					extrato = controladorExtrato.realizarSolitacaoExtrato();					
					for(String[] row : extrato) { model.addRow(row); }
					
					cardLayout.show(cardPanel, "Tela Extrato");
				
				} catch (SQLException e) {
					System.out.println(e);
				} catch (MovimentacaoInexistenteException e) {
					JOptionPane.showMessageDialog(TelaCliente.this, e);
					cardLayout.show(cardPanel, "Tela Inicial");
				}				
			}
		});
		
		mntmDeposito.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(cardPanel, "Tela Deposito");
			}
		});
		
		mntmTransferencia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(cardPanel, "Tela Transferencia");
			}
		});
		
		mntmSolicitarGerente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				int dialogButton = JOptionPane.showConfirmDialog(TelaCliente.this, "Deseja solicitar a visita do gerente?");
				
				if(dialogButton == 0) {
					try {
						controladorVisitaGerente.realizarOperacaoSolicitarVisitaGerente();
						JOptionPane.showMessageDialog(TelaCliente.this, "Operação realizada com sucesso");						
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					} catch (IllegalAccessException e) {
						JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					} catch (CampoObrigatorioException e) {
						JOptionPane.showMessageDialog(TelaCliente.this, "Falha ao executar operação.");
					}
				}
				
				cardLayout.show(cardPanel, "Tela Inicial");
			}
		});
		
		mntmSair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				TelaCliente.this.dispose();
			}
			
		});
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					iniciarTelaLogin();
				} catch (SQLException e) {
					System.out.println(e);
				} catch (AcessoException e) {
					System.out.println(e);
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				} catch (IllegalAccessException e) {
					System.out.println(e);
				} catch (CampoObrigatorioException e) {
					System.out.println(e);
				}
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}
		});		
	}
	
	public void iniciarTelaLogin() 
			throws SQLException, 
			AcessoException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		controladorSair.finalizarAplicacao(acesso);
		TelaLogin login = new TelaLogin();
		login.setVisible(true);
		thread.interrupt();
	}
}