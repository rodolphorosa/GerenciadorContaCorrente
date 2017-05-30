package uteis;

public class ValidadorSenha {
	
	public static boolean isSenhaValida(String senha) {
		return senha.matches("\\d{4}");
	}

}
