package br.edu.ifam.underattack.dao;

import org.hibernate.HibernateException;

import br.edu.ifam.underattack.model.Aluno;

/**
 * Data Access Object of Aluno entity.
 * 
 * @author Erika Silva
 */
public interface AlunoDao {

	/**
	 * Cadastra um usu�rio na base de dados
	 * @param aluno que ser� cadastrado
	 *
	 */
	void adiciona(Aluno aluno);

	/**
	 * Atualiza um usu�rio na base de dados
	 * @param aluno que ser� atualizado
	 *
	 */
	void atualiza(Aluno aluno);

	/**
	 * Verifica se existe um aluno com o login informado
	 *
	 * @return true, se j� existe um aluno com o login informado
	 */
	boolean existeAlunoComLogin(String login);

	/**
	 * Verifica se existe j� existe o e-mail informado em utiliza��o
	 *
	 * @return true, se j� existe um email em utiliza��o
	 */
	boolean emailEmUtilizacao(String email);

	/**
	 * Consulta um aluno por identificador
	 *
	 * @return o aluno encontrado
	 */
	Aluno consulta(Long id);

	/**
	 * Consulta um aluno por identificador (login/email) ou senha
	 *
	 * @return aluno encontrado
	 * @throws HibernateException, se existir mais de um usu�rio
	 */
	Aluno consulta(String identificador, String senha);

}
