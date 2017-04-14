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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * @author erika
 * 
 */
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 308126027524358198L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{campo.obrigatorio}")
	private String login;

	@NotNull(message = "{campo.obrigatorio}")
	@Email
	private String email;

	@NotNull(message = "{campo.obrigatorio}")
	private String senha;

	@OneToOne
	private NivelAluno nivelAluno;

	private int pontos;

	@OneToOne(cascade = CascadeType.ALL)
	private PocaoMagica pocaoMagica;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private List<AlunoParticipaFase> alunoParticipaFase;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER)
	private List<AlunoRealizaDesafio> alunoRealizaDesafio;

	@OneToMany(mappedBy = "aluno")
	private List<AlunoEncontraValorDeEntrada> alunoEncontraValorDeEntrada;

	@OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
	private List<AlunoEncontraClasseEquivalencia> alunoEncontraClasseEquivalencia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public NivelAluno getNivelAluno() {
		return nivelAluno;
	}

	public void setNivelAluno(NivelAluno nivelAluno) {
		this.nivelAluno = nivelAluno;
	}

	public PocaoMagica getPocaoMagica() {
		return pocaoMagica;
	}

	public void setPocaoMagica(PocaoMagica pocaoMagica) {
		this.pocaoMagica = pocaoMagica;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public List<AlunoParticipaFase> getAlunoParticipaFase() {
		return alunoParticipaFase;
	}

	public void setAlunoParticipaFase(
			List<AlunoParticipaFase> alunoParticipaFase) {
		this.alunoParticipaFase = alunoParticipaFase;
	}

	public List<AlunoRealizaDesafio> getAlunoRealizaDesafio() {
		return alunoRealizaDesafio;
	}

	public void setAlunoRealizaDesafio(
			List<AlunoRealizaDesafio> alunoRealizaDesafio) {
		this.alunoRealizaDesafio = alunoRealizaDesafio;
	}

	public List<AlunoEncontraValorDeEntrada> getAlunoEncontraValorDeEntrada() {
		return alunoEncontraValorDeEntrada;
	}

	public void setAlunoEncontraValorDeEntrada(
			List<AlunoEncontraValorDeEntrada> alunoEncontraValorDeEntrada) {
		this.alunoEncontraValorDeEntrada = alunoEncontraValorDeEntrada;
	}

	public List<AlunoEncontraClasseEquivalencia> getAlunoEncontraClasseEquivalencia() {
		return alunoEncontraClasseEquivalencia;
	}

	public void setAlunoEncontraClasseEquivalencia(
			List<AlunoEncontraClasseEquivalencia> alunoEncontraClasseEquivalencia) {
		this.alunoEncontraClasseEquivalencia = alunoEncontraClasseEquivalencia;
	}

	public void adicionarClasseEquivalencia(
			AlunoEncontraClasseEquivalencia alunoClasseEquivalencia) {
		if (!alunoEncontraClasseEquivalencia.contains(alunoClasseEquivalencia)) {
			this.alunoEncontraClasseEquivalencia.add(alunoClasseEquivalencia);
		}

	}

	public void adicionaValorEntrada(AlunoEncontraValorDeEntrada valorEntrada) {
		if (!alunoEncontraValorDeEntrada.contains(valorEntrada)) {
			this.alunoEncontraValorDeEntrada.add(valorEntrada);
			valorEntrada.setAluno(this);
		}

	}

}
