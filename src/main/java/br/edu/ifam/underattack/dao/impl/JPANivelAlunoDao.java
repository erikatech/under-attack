package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.NivelAlunoDao;
import br.edu.ifam.underattack.model.NivelAluno;

/**
 * Implementação JPA para AlunoDao
 *
 * @author Érika Silva
 */
@Transactional
public class JPANivelAlunoDao implements NivelAlunoDao {

	private final EntityManager manager;

	/**
	 * @deprecated apenas para CDI
	 */
	JPANivelAlunoDao() {
		this(null);
	}
	
	/**
	 * Cria um novo JpaAlunoDao
	 * 
	 * @param entityManager JPA's EntityManager.
	 */
	@Inject
	public JPANivelAlunoDao(EntityManager manager) {
		this.manager = manager;
	}



	@Override
	public NivelAluno consulta(Long id) {
		TypedQuery<NivelAluno> query = this.manager.createQuery(
				"select n from NivelAluno n where n.id =:id", NivelAluno.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public List<NivelAluno> todosOsNiveis() {
		TypedQuery<NivelAluno> query = this.manager.createQuery(
				"select n from NivelAluno n", NivelAluno.class);
		return query.getResultList();
	}
	
	


}
