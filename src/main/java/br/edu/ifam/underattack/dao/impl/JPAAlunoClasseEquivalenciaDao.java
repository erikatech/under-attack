package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.AlunoClasseEquivalenciaDao;
import br.edu.ifam.underattack.model.AlunoEncontraClasseEquivalencia;

@Transactional
public class JPAAlunoClasseEquivalenciaDao implements
        AlunoClasseEquivalenciaDao {

	private final EntityManager manager;

	@Inject
	public JPAAlunoClasseEquivalenciaDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAAlunoClasseEquivalenciaDao() {
		this(null);
	}

	@Override
	public void associa(
			AlunoEncontraClasseEquivalencia alunoEncontraClasseEquivalencia) {
		this.manager.persist(alunoEncontraClasseEquivalencia);
	}

	@Override
	public List<AlunoEncontraClasseEquivalencia> todos(Long idAluno) {
		TypedQuery<AlunoEncontraClasseEquivalencia> query = this.manager
				.createQuery(
						"select ac from AlunoEncontraClasseEquivalencia ac where ac.aluno.id=:idAlunuo",
						AlunoEncontraClasseEquivalencia.class);
		query.setParameter("idAluno",idAluno);
		return query.getResultList();
	}

}
