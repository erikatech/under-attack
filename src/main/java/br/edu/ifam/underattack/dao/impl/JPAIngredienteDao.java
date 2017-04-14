package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.IngredienteDao;
import br.edu.ifam.underattack.model.Ingrediente;

@Transactional
public class JPAIngredienteDao implements IngredienteDao {

	private final EntityManager manager;

	@Inject
	public JPAIngredienteDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAIngredienteDao() {
		this(null);
	}

	@Override
	public void salva(Ingrediente ingrediente) {
		this.manager.persist(ingrediente);
	}

	@Override
	public List<Ingrediente> todos() {
		TypedQuery<Ingrediente> query = manager.createQuery(
				"select i from Ingrediente i", Ingrediente.class);
		return query.getResultList();
	}

	@Override
	public Ingrediente consulta(Long id) {
		TypedQuery<Ingrediente> query = this.manager.createQuery(
				"select i from Ingrediente i where i.id =:id",
				Ingrediente.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
}
