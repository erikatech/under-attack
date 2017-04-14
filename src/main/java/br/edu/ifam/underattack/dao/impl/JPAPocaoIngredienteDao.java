package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.PocaoIngredienteDAO;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;

@Transactional
public class JPAPocaoIngredienteDAO implements PocaoIngredienteDAO {

	private final EntityManager manager;

	@Inject
	public JPAPocaoIngredienteDAO(EntityManager manager) {
		this.manager = manager;
	}

	JPAPocaoIngredienteDAO() {
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
