package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.IngredienteDAO;
import br.edu.ifam.underattack.model.ImagemIngrediente;
import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.model.enums.TipoImagemIngrediente;

@Transactional
public class JPAIngredienteDAO implements IngredienteDAO {

	private final EntityManager manager;

	@Inject
	public JPAIngredienteDAO(EntityManager manager) {
		this.manager = manager;
	}

	JPAIngredienteDAO() {
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
	
	@Override
	public ImagemIngrediente buscarImagemPorTipo(Ingrediente ingrediente) {
		TypedQuery<ImagemIngrediente> query = this.manager.createQuery(
				"select im from Ingrediente i JOIN i.imagens im where i.id =:id and im.tipo=:tipo",
				ImagemIngrediente.class);
		query.setParameter("id", ingrediente.getId());
		query.setParameter("tipo", TipoImagemIngrediente.CINZA);
		return query.getSingleResult();
	}

}
