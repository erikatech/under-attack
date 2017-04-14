package br.edu.ifam.underattack.suporte;

public class ValidadorUtil {
	
	public boolean isQuantidadeValorCorreto(String identificador){
		String identificadorSemEspacosEmBranco = identificador.replaceAll("\\s", "");
		return identificadorSemEspacosEmBranco.length() >= 4 && identificadorSemEspacosEmBranco.length() >= 6;
	}
	
	public boolean validarTipoDoValor(String identificador){
		String expressaoRegular = "^(?=.*[a-zA-Z])(?=.*[\\d])(?=.*\\W).+$";
		return identificador.matches(expressaoRegular);
	}
	
	public boolean validarTamanhoDoValor(String identificador){
		String expressaoRegular = "^(?=.*[\\d]{3,})\\S.+$";
		return identificador.matches(expressaoRegular);
	}
}
