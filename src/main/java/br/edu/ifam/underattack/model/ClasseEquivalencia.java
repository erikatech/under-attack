package br.edu.ifam.underattack.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.ifam.underattack.model.enums.Dificuldade;
import br.edu.ifam.underattack.model.enums.TipoClasseEquivalencia;

@Entity
@Table(name = "classe_equivalencia")
public class ClasseEquivalencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2777783985850360586L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;

	private String expressaoRegular;

	private Boolean bugExistente;

	@Enumerated(EnumType.STRING)
	private TipoClasseEquivalencia tipo;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private CasoTeste casoTeste;

	@OneToOne(fetch = FetchType.EAGER)
	private Ingrediente ingrediente;

	@ManyToOne(fetch = FetchType.EAGER)
	private ValorDeEntrada valorDeEntrada;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
	private List<AlunoEncontraClasseEquivalencia> alunoEncontraClasseEquivalencia;

	@Enumerated(EnumType.STRING)
	private Dificuldade dificuldade;

	public ClasseEquivalencia() {
		// TODO Auto-generated constructor stub
	}

	public ClasseEquivalencia(String descricao, String expressaoRegular,
			Boolean bugExistente, TipoClasseEquivalencia tipo) {
		this.descricao = descricao;
		this.expressaoRegular = expressaoRegular;
		this.bugExistente = bugExistente;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getExpressaoRegular() {
		return expressaoRegular;
	}

	public void setExpressaoRegular(String expressaoRegular) {
		this.expressaoRegular = expressaoRegular;
	}

	public Boolean getBugExistente() {
		return bugExistente;
	}

	public void setBugExistente(Boolean bugExistente) {
		this.bugExistente = bugExistente;
	}

	public TipoClasseEquivalencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoClasseEquivalencia tipo) {
		this.tipo = tipo;
	}

	public CasoTeste getCasoTeste() {
		return casoTeste;
	}

	public void setCasoTeste(CasoTeste casoTeste) {
		this.casoTeste = casoTeste;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public ValorDeEntrada getValorDeEntrada() {
		return valorDeEntrada;
	}

	public void setValorDeEntrada(ValorDeEntrada valorDeEntrada) {
		this.valorDeEntrada = valorDeEntrada;
	}

	public List<AlunoEncontraClasseEquivalencia> getAlunoEncontraClasseEquivalencia() {
		return alunoEncontraClasseEquivalencia;
	}

	public void setAlunoEncontraClasseEquivalencia(
			List<AlunoEncontraClasseEquivalencia> alunoEncontraClasseEquivalencia) {
		this.alunoEncontraClasseEquivalencia = alunoEncontraClasseEquivalencia;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	public int getPontos(){
		return this.dificuldade.getPontos();
	}
	
	public boolean hasItemPocao(){
		return this.ingrediente != null;
	}

}
