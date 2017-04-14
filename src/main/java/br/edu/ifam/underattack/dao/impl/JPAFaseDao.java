package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.FaseDao;
import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.Objetivo;

@Transactional
public class JPAFaseDao implements FaseDao {

	private final EntityManager manager;

	@Inject
	public JPAFaseDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAFaseDao() {
		this(null);
	}

	@Override
	public void adiciona(Fase fase) {
		this.manager.persist(fase);
	}

	@Override
	public List<Fase> todasAsFases() {
		return manager.createQuery("select f from Fase f", Fase.class)
				.getResultList();
	}

	@Override
	public List<Objetivo> todosOsDesafios(Long idFase) {
		TypedQuery<Objetivo> query = this.manager.createQuery("select o from Objetivo o where o.fase.id =:id",Objetivo.class);
		query.setParameter("id", idFase);
		return query.getResultList();
	}

	@Override
	public void adiciona(Objetivo objetivo) {
		this.manager.persist(objetivo);
	}

}
