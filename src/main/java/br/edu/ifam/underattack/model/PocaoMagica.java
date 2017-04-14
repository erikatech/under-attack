package br.edu.ifam.underattack.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pocao_magica")
public class PocaoMagica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6057291408729729996L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "pocaoMagica",cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
	private List<PocaoMagicaIngrediente> pocaoIngredienteList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PocaoMagicaIngrediente> getPocaoIngredienteList() {
		return pocaoIngredienteList;
	}

	public void setPocaoIngredienteList(
			List<PocaoMagicaIngrediente> pocaoIngredienteList) {
		this.pocaoIngredienteList = pocaoIngredienteList;
		for (PocaoMagicaIngrediente pocaoMagicaIngrediente : pocaoIngredienteList) {
			pocaoMagicaIngrediente.setPocaoMagica(this);
		}
	}


}
