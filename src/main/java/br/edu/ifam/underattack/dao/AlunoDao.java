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
	 * Cadastra um usuário na base de dados
	 * @param o aluno que será cadastrado
	 * 
	 */
	void adiciona(Aluno aluno);

	/**
	 * Atualiza um usuário na base de dados
	 * @param o aluno que será atualizado
	 * 
	 */
	void atualiza(Aluno aluno);
	
	/**
	 * Verifica se existe um aluno com o login informado
	 *
	 * @return true, se já existe um aluno com o login informado
	 */
	boolean existeAlunoComLogin(String login);
	
	/**
	 * Verifica se existe já existe o e-mail informado em utilização
	 *
	 * @return true, se já existe um email em utilização
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
	 * @throws HibernateException, se existir mais de um usuário
	 */
	Aluno consulta(String identificador, String senha);

}
