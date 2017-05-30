package testes.unitarios;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import excecoes.CampoObrigatorioException;
import modelos.Conta;
import modelos.ContaNormal;
import modelos.Movimentacao;
import modelos.TipoMovimentacao;

public class MovimentacaoTeste {	
	
	private Conta conta;
	private Date data;
	private Time hora;
	
	@Before
	public void criarConta() {
		conta = new ContaNormal((long) 1, "11111", 0);
		data = new Date(Calendar.getInstance().getTimeInMillis());
		hora = new Time(Calendar.getInstance().getTimeInMillis());
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testeAtributoContaNulo() throws IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {
		new Movimentacao(null, data, hora, 0, TipoMovimentacao.DEBITO);
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testeAtributoDataNulo() throws IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {
		new Movimentacao(conta, null, hora, 0, TipoMovimentacao.DEBITO);
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testeAtributoHoraNulo() throws IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {
		new Movimentacao(conta, data, null, 0, TipoMovimentacao.DEBITO);
	}
	
	@Test(expected = CampoObrigatorioException.class)
	public void testeAtributoTipoMovimentacaoNulo() throws IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {
		new Movimentacao(conta, data, hora, 0, null);
	}
}