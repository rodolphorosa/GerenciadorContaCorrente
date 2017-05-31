package controladores;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;

import excecoes.CampoObrigatorioException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.ContaNaoEncontradaException;
import excecoes.OperacaoInvalidaException;
import excecoes.SaldoInsuficienteException;
import mapeadores.ContaMapper;
import mapeadores.MovimentacaoMapper;
import modelos.Conta;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class RealizarTransferencia {
	
	private Conta conta;
	private ContaMapper contaMapper;
	private MovimentacaoMapper movimentacaoMapper;
	
	/**
	 * @param conta Conta.
	 * 
	 * */
	public RealizarTransferencia(Conta conta) {
		this.conta = conta;
		movimentacaoMapper = MovimentacaoMapper.getInstance();
		contaMapper = ContaMapper.getInstance();
	}
	
	/**
	 * Transfere um valor de uma conta para outra.
	 * 
	 * A conta do destinatario eh solicitada. 
	 * Se a conta fora mesma que a do cedente, eh lancada uma excecao.
	 * 
	 * Se nenhum erro ocorrer, sao debitados o valor e a taxa de transferencia da conta
	 * do cedente, e depositado o valor na conta do destinatario.
	 * 
	 * Sao registradas duas movimentacoes (TRASNFERENCIA e DEBITO) 
	 * com valores negativos na conta do cedente,
	 * e uma (TRANSFERENCIA) com valor positivo na contan do destinatario.
	 * 
	 * @param numeroContaDestinatario Numero da conta do destinatario
	 * @param quantia Valor a ser transferido.
	 * 
	 * */
	public void realizarOperacaoTransferencia(String numeroContaDestinatario, double quantia) 
			throws ClienteNaoEncontradoException, 
			SQLException, 
			ContaNaoEncontradaException, 
			OperacaoInvalidaException, 
			SaldoInsuficienteException, 
			ParseException, 
			IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		Conta contaDestinatario =  contaMapper.obterPorNumero(numeroContaDestinatario);
		
		if (contaDestinatario == null) {
			throw new ContaNaoEncontradaException();
		} else if (contaDestinatario.getNumero().equals(conta.getNumero())) {
			throw new OperacaoInvalidaException("Não é possível transferir para a mesma conta.");
		}
		
		conta.debitarTransferencia(quantia);
		contaDestinatario.depositar(quantia);
		
		Date data = new Date(Calendar.getInstance().getTimeInMillis());
		Time hora = new Time(Calendar.getInstance().getTimeInMillis());
		
		Movimentacao movimentacaoCedente = new Movimentacao(
					conta, 
					data, 
					hora, 
					-1 * quantia, 
					TipoMovimentacao.TRANSFERENCIA);
		
		Movimentacao debitoTaxaTransferencia = new Movimentacao(
					conta, 
					data, 
					hora, 
					-1 * conta.calcularTaxaTransferencia(quantia),
					TipoMovimentacao.DEBITO);
		
		Movimentacao movimentacaoDestinatario = new Movimentacao(
					contaDestinatario, 
					data, 
					hora, 
					quantia, 
					TipoMovimentacao.TRANSFERENCIA);
		
		movimentacaoMapper.adicionar(movimentacaoCedente);
		movimentacaoMapper.adicionar(debitoTaxaTransferencia);
		movimentacaoMapper.adicionar(movimentacaoDestinatario);
		contaMapper.atualizar(conta);
		contaMapper.atualizar(contaDestinatario);
	}
}