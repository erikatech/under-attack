package br.edu.ifam.underattack.to;

import java.io.Serializable;

public class TestesExceptionsTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929185845166177237L;

	private String descricaoErro;

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}

}
