package br.edu.ifam.underattack.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caso_teste")
public class CasoTeste implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2462778066594679899L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String resultadoEsperado;

	private String saida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResultadoEsperado() {
		return resultadoEsperado;
	}

	public void setResultadoEsperado(String resultadoEsperado) {
		this.resultadoEsperado = resultadoEsperado;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

}
