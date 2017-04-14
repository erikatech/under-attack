package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.ClasseDAO;
import br.edu.ifam.underattack.model.Classe;

@Transactional
public class JPAClasseDAO implements ClasseDAO {

	private final EntityManager manager;

	@Inject
	public JPAClasseDAO(EntityManager manager) {
		this.manager = manager;
	}

	JPAClasseDAO() {
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
