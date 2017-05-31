package modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import annotations.CampoObrigatorio;
import excecoes.AcessoException;
import excecoes.CampoObrigatorioException;

public class Acesso {

	@CampoObrigatorio(obrigatorio = true)
	private Conta conta;

	@CampoObrigatorio(obrigatorio = false)
	private Date dataLogin;

	@CampoObrigatorio(obrigatorio = false)
	private Time horaLogin;

	@CampoObrigatorio(obrigatorio = false)
	private Date dataLogout;

	@CampoObrigatorio(obrigatorio = false)
	private Time horaLogout;

	public Acesso(Conta conta, Date dataLogin, Time horaLogin, Date dataLogout, Time horaLogout) {
		this.conta = conta;
		this.dataLogin = dataLogin;
		this.horaLogin = horaLogin;
		this.dataLogout = dataLogout;
		this.horaLogout = horaLogout;
	}

	public Acesso(Conta conta)
			throws IllegalArgumentException, IllegalAccessException, AcessoException, CampoObrigatorioException {
		this.conta = conta;
		Calendar c = Calendar.getInstance();
		dataLogin = new Date(c.getTimeInMillis());
		horaLogin = new Time(c.getTimeInMillis());

		validar();
	}

	public Conta getConta() {
		return conta;
	}

	public Date getDataLogin() {
		return dataLogin;
	}

	public Time getHoraLogin() {
		return horaLogin;
	}

	public Date getDataLogout() {
		return dataLogout;
	}

	public Time getHoraLogout() {
		return horaLogout;
	}

	public void iniciarDataHoraLogout()
			throws AcessoException, IllegalArgumentException, IllegalAccessException, CampoObrigatorioException {

		Calendar c = Calendar.getInstance();
		dataLogout = new Date(c.getTimeInMillis());
		horaLogout = new Time(c.getTimeInMillis());

		validar();
	}

	private void validar()
			throws AcessoException, CampoObrigatorioException, IllegalArgumentException, IllegalAccessException {
		if (dataLogout != null && dataLogout.before(dataLogin)) {
			throw new AcessoException();
		} else if (horaLogout != null && horaLogout.before(horaLogin)) {
			throw new AcessoException();
		}

		Class<?> classe = this.getClass();
		Field[] atributos = classe.getDeclaredFields();

		for (Field f : atributos) {
			f.setAccessible(true);
			CampoObrigatorio co = f.getAnnotation(CampoObrigatorio.class);
			if (co.obrigatorio() && f.get(this) == null) {
				throw new CampoObrigatorioException(f.getName());
			}
		}
	}

	public String toString() {
		return String.format("%s: { login: %s %s, logout: %s %s }", conta.getNumero(), dataLogin, horaLogin, dataLogout,
				horaLogout);
	}
}