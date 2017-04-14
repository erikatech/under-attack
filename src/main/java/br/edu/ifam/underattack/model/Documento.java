package br.edu.ifam.underattack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6153359160217590956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	
	@Lob
	private String descricao;

	@OneToMany(mappedBy = "documento", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ValorDeEntrada> valoresEntrada;

	public Documento() {
		this.valoresEntrada = new ArrayList<ValorDeEntrada>();
	}

	public Documento(String titulo, String descricao) {
		this();
		this.titulo = titulo;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ValorDeEntrada> getValoresEntrada() {
		Collections.shuffle(this.valoresEntrada);
		return this.valoresEntrada;
	}

	public void setValoresEntrada(List<ValorDeEntrada> valoresEntrada) {
		this.valoresEntrada = valoresEntrada;
		for (ValorDeEntrada valorDeEntrada : valoresEntrada) {
			valorDeEntrada.setDocumento(this);
		}
	}

	public void adicionarValorEntrada(ValorDeEntrada valorEntrada) {
		if (this.valoresEntrada.contains(valorEntrada)) {
			throw new RuntimeException("Elemento já existe na lista");
		}

		if (null == valorEntrada) {
			throw new RuntimeException("Elemento não pode ser nulo");
		}

		this.valoresEntrada.add(valorEntrada);
		valorEntrada.setDocumento(this);
	}

}
