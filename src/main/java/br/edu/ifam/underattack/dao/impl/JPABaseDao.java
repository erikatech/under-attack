package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.BaseDao;
import br.edu.ifam.underattack.model.Programa;

@Transactional
public class JPABaseDao implements BaseDao {

	private final EntityManager manager;

	@Inject
	public JPABaseDao(EntityManager manager) {
		this.manager = manager;
	}

	JPABaseDao() {
		this(null);
	}
	
	@Override
	public Programa consulta(Long id) {
		return this.manager.find(Programa.class, id);
	}

}
