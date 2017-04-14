package br.edu.ifam.underattack.to;

import java.io.Serializable;

public class RetornoTesteTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929185845166177237L;

	private String descricao;

	private int pontos;

	private boolean erro;

	private String nomeMetodo;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

}
