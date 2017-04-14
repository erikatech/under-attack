package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.AlunoFaseDao;
import br.edu.ifam.underattack.model.AlunoParticipaFase;

@Transactional
public class JPAAlunoFaseDao implements AlunoFaseDao {

	private final EntityManager manager;

	@Inject
	public JPAAlunoFaseDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAAlunoFaseDao() {
		this(null);
	}

	@Override
	public void adiciona(AlunoParticipaFase alunoParticipaFase) {
		this.manager.persist(alunoParticipaFase);
	}

	@Override	
	public List<AlunoParticipaFase> todasAsFases(Long idAluno) {
		TypedQuery<AlunoParticipaFase> query = this.manager
				.createQuery(
						"select af from AlunoParticipaFase af where af.aluno.id =:idAluno",
						AlunoParticipaFase.class);
		query.setParameter("idAluno", idAluno);
		return query.getResultList();
	}
	
	@Override
	public AlunoParticipaFase consultaFaseAlunoPorId(Long idFaseAluno) {
		TypedQuery<AlunoParticipaFase> query = this.manager
				.createQuery("select af from AlunoParticipaFase af where af.id =:idFaseAluno",
						AlunoParticipaFase.class);
		query.setParameter("idFaseAluno", idFaseAluno);
		return query.getSingleResult();
	}

}
