package br.edu.ifam.underattack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "programa")
public class Programa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604332821695542568L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Documento documento;
	
	@OneToMany(mappedBy = "programa")
	private List<Classe> classes;

	public Programa() {
		this.classes = new ArrayList<Classe>();
	}

	public Programa(Documento documento) {
		this.documento = documento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}
	
	

}
