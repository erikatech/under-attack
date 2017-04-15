package br.edu.ifam.underattack.dao;

import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.Professor;
import org.hibernate.HibernateException;

import javax.persistence.NoResultException;

/**
 * Data Access Object of Aluno entity.
 * 
 * @author Erika Silva
 */
public interface ProfessorDao {

	/**
	 * Cadastra um professor na base de dados
	 * @param professor que sera cadastrado
	 *
	 */
	void adiciona(Professor professor);

	/**
	 * Atualiza um professor na base de dados
	 * @param professor que sera atualizado
	 *
	 */
	void atualiza(Professor professor);


	/**
	 * Consulta um professor por identificador
	 *
	 * @return o professor encontrado
	 */
	Professor consulta(Long id);

	/**
	 * Consulta pra realizar login
	 * @param login
	 * @param senha
	 * @return
	 */
	Professor consulta(String login, String senha);

	/**
	 * Consulta um professor por login
	 * @param login
	 * @return
	 */
	Professor findByLogin(String login);


}
