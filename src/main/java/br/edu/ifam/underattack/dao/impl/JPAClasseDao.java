package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.ClasseDao;
import br.edu.ifam.underattack.model.Classe;

@Transactional
public class JPAClasseDao implements ClasseDao {

	private final EntityManager manager;

	@Inject
	public JPAClasseDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAClasseDao() {
		this(null);
	}

	@Override
	public void adiciona(Classe classe) {
		this.manager.persist(classe);
	}

	@Override
	public Classe consulta(Long id) {
		return this.manager.find(Classe.class, id);
	}

}
