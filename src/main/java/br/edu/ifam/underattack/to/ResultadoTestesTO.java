package br.edu.ifam.underattack.to;

import java.io.Serializable;

public class ResultadoTestesTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929185845166177237L;

	private int testesEscritos;

	private int totalTestes;

	private double quantidadeEstrelas;

	private int pontuacaoFinal;

	public int getTestesEscritos() {
		return testesEscritos;
	}

	public void setTestesEscritos(int testesEscritos) {
		this.testesEscritos = testesEscritos;
	}

	public int getTotalTestes() {
		return totalTestes;
	}

	public void setTotalTestes(int totalTestes) {
		this.totalTestes = totalTestes;
	}

	public double getQuantidadeEstrelas() {
		return quantidadeEstrelas;
	}

	public void setQuantidadeEstrelas(double quantidadeEstrelas) {
		this.quantidadeEstrelas = quantidadeEstrelas;
	}

	public int getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public void setPontuacaoFinal(int pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
	}

}
