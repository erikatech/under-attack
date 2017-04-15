package br.edu.ifam.underattack.dao.impl;

import br.edu.ifam.underattack.dao.ProfessorDao;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.Professor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * Implementação JPA para ProfessorDao
 *
 * @author Érika Silva
 */
@Transactional
public class JPAProfessorDao implements ProfessorDao {

	private final EntityManager manager;

	@Inject
	public JPAProfessorDao(EntityManager manager) {
		this.manager = manager;
	}


	@Override
	public void adiciona(Professor professor) {
		this.manager.persist(professor);

	}

	@Override
	public void atualiza(Professor professor) {
		this.manager.merge(professor);
	}

	@Override
	public Professor consulta(Long id) {
		return this.manager.find(Professor.class, id);
	}

	@Override
	public Professor consulta(String login, String senha){
		try {
			TypedQuery<Professor> query = this.manager
					.createQuery(
							"select p from Professor p where p.login =:login and p.senha=:senha",
							Professor.class);
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Professor findByLogin(String login) {
		try {
			TypedQuery<Professor> query = this.manager
					.createQuery(
							"select p from Professor p where p.login =:login",
							Professor.class);
			query.setParameter("login", login);
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;
	}
}
