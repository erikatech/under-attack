package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.AlunoDao;
import br.edu.ifam.underattack.model.Aluno;

/**
 * Implementação JPA para AlunoDao
 *
 * @author Érika Silva
 */
@Transactional
public class JPAAlunoDao implements AlunoDao {

	private final EntityManager manager;

	/**
	 * @deprecated apenas para CDI
	 */
	JPAAlunoDao() {
		this(null);
	}
	
	/**
	 * Cria um novo JpaAlunoDao
	 * 
	 * @param entityManager JPA's EntityManager.
	 */
	@Inject
	public JPAAlunoDao(EntityManager manager) {
		this.manager = manager;
	}


	@Override
	public void adiciona(Aluno aluno) {
		if (aluno.getId() == null) {
			this.manager.persist(aluno);
		} else {
			this.manager.merge(aluno);
		}
	}

	@Override
	public void atualiza(Aluno aluno) {
		this.manager.merge(aluno);
	}

	@Override
	public Aluno consulta(Long id) {
		TypedQuery<Aluno> query = this.manager.createQuery(
				"select a from Aluno a where a.id =:id", Aluno.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Aluno consulta(String identificador, String senha) {
		try {
			TypedQuery<Aluno> query = this.manager
					.createQuery(
							"select a from Aluno a where (a.login =:identificador OR a.email=:identificador) and a.senha=:senha",
							Aluno.class);
			query.setParameter("identificador", identificador);
			query.setParameter("senha", senha);
			Aluno aluno = query.getSingleResult();
			aluno.getAlunoRealizaDesafio().size();
			return aluno;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean existeAlunoComLogin(String login) {
		Long count = manager
				.createQuery("select count(a) from Aluno a where a.login = :login", Long.class)
				.setParameter("login", login)
				.getSingleResult();
		return count > 0;
	}

	@Override
	public boolean emailEmUtilizacao(String email) {
		Long count = manager
				.createQuery("select count(a) from Aluno a where a.email = :email", Long.class)
				.setParameter("email", email)
				.getSingleResult();
		return count > 0;
	}
	
	


}
