package br.edu.ifam.underattack.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author erika
 * 
 */
@Entity
@Table(name = "professor")
public class Professor implements Serializable {

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
	private String senha;

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
