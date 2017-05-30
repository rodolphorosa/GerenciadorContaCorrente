package uteis;

public class ValidadorNumeroConta {
	
	public static boolean isNumeroContaValida(String numero) {
		return numero.matches("\\d{5}");
	}
}
