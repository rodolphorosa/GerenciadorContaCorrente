package modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;

import annotations.CampoObrigatorio;
import excecoes.CampoObrigatorioException;

public class Movimentacao {
	
	@CampoObrigatorio(obrigatorio = false)
	private Long id;
	
	@CampoObrigatorio(obrigatorio = true)
	private Conta conta;
	
	@CampoObrigatorio(obrigatorio = true)
	private Time hora;
	
	@CampoObrigatorio(obrigatorio = true)
	private Date data;
	
	@CampoObrigatorio(obrigatorio = true)
	private double valor;
	
	@CampoObrigatorio(obrigatorio = true)
	private TipoMovimentacao tipo;
	
	/**
	 * @param id Identificador da movimentacao.
	 * @param conta Conta do cliente.
	 * @param data Data da movimentacao.
	 * @param hora Hora da movimentacao.
	 * @param valor Valor da movimentacao.
	 * @param tipo Tipo da movimentacao.
	 * 
	 * */
	public Movimentacao(Long id, Conta conta, Date data, Time hora, double valor, TipoMovimentacao tipo) {
		this.id = id;
		this.conta = conta;
		this.data = data;
		this.hora = hora;
		this.valor = valor;
		this.tipo = tipo;
	}
	
	/**
	 * @param conta Conta do cliente.
	 * @param data Data da movimentacao.
	 * @param hora Hora da movimentacao.
	 * @param valor Valor da movimentacao.
	 * @param tipo Tipo da movimentacao.
	 * 
	 * */
	public Movimentacao(Conta conta, Date data, Time hora, double valor, TipoMovimentacao tipo) 
			throws IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		this.conta = conta;
		this.data = data;
		this.hora = hora;
		this.valor = valor;
		this.tipo = tipo;
		
		validar();
	}

	public Long getId() {
		return id;
	}

	public Conta getConta() {
		return conta;
	}

	public Time getHora() {
		return hora;
	}

	public Date getData() {
		return data;
	}

	public double getValor() {
		return valor;
	}
	
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	
	/**
	 * Verifica se todos os atributos obrigatorios foram inicializados.
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws CampoObrigatorioException
	 * 
	 * */
	private void validar() 
			throws IllegalArgumentException, 
			IllegalAccessException, 
			CampoObrigatorioException {
		
		Class<?> classe = this.getClass(); 
		Field[] atributos = classe.getDeclaredFields();
		
		for(Field f : atributos) {
			f.setAccessible(true);
			CampoObrigatorio co = f.getAnnotation(CampoObrigatorio.class);
			if(co.obrigatorio() && f.get(this) == null) {
				throw new CampoObrigatorioException(f.getName());
			}
		}
		
	}
	
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String strValor = valor >= 0 ? nf.format(valor) : String.format("(%s)", nf.format(valor));		
		return String.format("%s\t%s\t%s\t%s\n", data, hora, tipo, strValor);
	}

}
