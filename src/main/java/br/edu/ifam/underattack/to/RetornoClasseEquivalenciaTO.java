package br.edu.ifam.underattack.to;

import java.io.Serializable;

public class RetornoClasseEquivalenciaTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4929185845166177237L;

	private boolean repetiuClasse;

	private boolean encontrouClasse;

	public boolean isRepetiuClasse() {
		return repetiuClasse;
	}

	public void setRepetiuClasse(boolean repetiuClasse) {
		this.repetiuClasse = repetiuClasse;
	}

	public boolean isEncontrouClasse() {
		return encontrouClasse;
	}

	public void setEncontrouClasse(boolean encontrouClasse) {
		this.encontrouClasse = encontrouClasse;
	}

}
