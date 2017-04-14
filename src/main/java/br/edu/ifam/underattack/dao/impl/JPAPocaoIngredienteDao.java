package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.PocaoIngredienteDao;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;

@Transactional
public class JPAPocaoIngredienteDao implements PocaoIngredienteDao {

	private final EntityManager manager;

	@Inject
	public JPAPocaoIngredienteDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAPocaoIngredienteDao() {
		this(null);
	}

	@Override
	public void associa(PocaoMagicaIngrediente pocaoIngrediente) {
		this.manager.persist(pocaoIngrediente);
	}
	
	@Override
	public void atualiza(PocaoMagicaIngrediente pocaoIngrediente) {
		this.manager.merge(pocaoIngrediente);
	}

}
