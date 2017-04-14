package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.PocaoMagicaDao;
import br.edu.ifam.underattack.model.PocaoMagica;

@Transactional
public class JPAPocaoMagicaDao implements PocaoMagicaDao {

	private final EntityManager manager;

	@Inject
	public JPAPocaoMagicaDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAPocaoMagicaDao() {
		this(null);
	}

	@Override
	public void adiciona(PocaoMagica pocaoMagica) {
		this.manager.persist(pocaoMagica);
	}
	
	@Override
	public void atualiza(PocaoMagica pocaoMagica) {
		this.manager.merge(pocaoMagica);
	}

}
