package br.edu.ifam.underattack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.ifam.underattack.model.enums.IndicadorFase;
import br.edu.ifam.underattack.model.enums.NivelDesafio;

@Entity
@Table(name = "desafio")
public class Desafio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1822263825561055884L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@Lob
	private String descricao;

	@Enumerated(EnumType.STRING)
	private NivelDesafio nivel;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Programa programa;

	@OneToMany(mappedBy = "desafio", fetch = FetchType.EAGER)
	private List<AlunoRealizaDesafio> alunoRealizaDesafio;

	private String classeCSS;

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

	public NivelDesafio getNivel() {
		return nivel;
	}

	public void setNivel(NivelDesafio nivel) {
		this.nivel = nivel;
	}

	public List<AlunoRealizaDesafio> getAlunoRealizaDesafio() {
		return alunoRealizaDesafio;
	}

	public void setAlunoRealizaDesafio(
			List<AlunoRealizaDesafio> alunoRealizaDesafio) {
		this.alunoRealizaDesafio = alunoRealizaDesafio;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public String getClasseCSS() {
		return classeCSS;
	}

	public void setClasseCSS(String classeCSS) {
		this.classeCSS = classeCSS;
	}

	public int getTotalTestesDesafio() {
		int totalTestesDoDesafio = 0;
		Set<ValorDeEntrada> valoresSemRepeticao = new HashSet<ValorDeEntrada>();
		for (ValorDeEntrada valorDeEntrada : this.programa.getDocumento()
				.getValoresEntrada()) {
			valoresSemRepeticao.add(valorDeEntrada);
		}
		for (ValorDeEntrada valor : valoresSemRepeticao) {
			if (valor.isValorEntradaCorreto()) {
				totalTestesDoDesafio += valor.getClassesEquivalencia().size();
			}
		}
		return totalTestesDoDesafio;
	}

	public List<ClasseEquivalencia> getClassesEquivalencia() {
		Set<ValorDeEntrada> valoresCorretos = new HashSet<ValorDeEntrada>();
		List<ClasseEquivalencia> classesEquivalencia = new ArrayList<ClasseEquivalencia>();
		List<ValorDeEntrada> valoresEntrada = this.programa.getDocumento()
				.getValoresEntrada();
		for (ValorDeEntrada valorDeEntrada : valoresEntrada) {
			if (valorDeEntrada.isValorEntradaCorreto()) {
				valoresCorretos.add(valorDeEntrada);
			}
		}

		for (ValorDeEntrada valor : valoresCorretos) {
			classesEquivalencia.addAll(valor.getClassesEquivalencia());
		}
		return classesEquivalencia;
	}

	public String getSituacaoTestadores(Long idAluno) {
		for (AlunoRealizaDesafio ad : alunoRealizaDesafio) {
			if (ad.getDesafio().getId().equals(this.getId())
					&& ad.getAluno().getId().equals(idAluno)
					&& ad.getIndicadorFase().equals(
							IndicadorFase.FASE_TESTADORES)) {
				return ad.getSituacaoDesafio().getLabel();
			}
		}
		return null;
	}

	public String getSituacaoDesenvolvedores(Long idAluno) {
		for (AlunoRealizaDesafio ad : alunoRealizaDesafio) {
			if (ad.getDesafio().getId().equals(this.getId())
					&& ad.getAluno().getId().equals(idAluno)
					&& ad.getIndicadorFase().equals(
							IndicadorFase.FASE_DESENVOLVEDORES)) {
				return ad.getSituacaoDesafio().getLabel();
			}
		}
		return null;
	}

}
