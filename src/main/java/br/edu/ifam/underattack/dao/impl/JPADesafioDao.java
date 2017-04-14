package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.DesafioDAO;
import br.edu.ifam.underattack.model.Desafio;

@Transactional
public class JPADesafioDAO implements DesafioDAO {

	private final EntityManager manager;

	@Inject
	public JPADesafioDAO(EntityManager manager) {
		this.manager = manager;
	}

	JPADesafioDAO() {
		this(null);
	}

	@Override
	public List<Desafio> listaTodos() {
		return manager.createQuery("select d from Desafio d", Desafio.class)
				.getResultList();
	}
	
	@Override
	public Desafio buscaPorId(Long id) {
		TypedQuery<Desafio> query = manager.createQuery("select d from Desafio d where id=:id", Desafio.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	@Override
	public void atualiza(Desafio desafio) {
		this.manager.merge(desafio);
		
	}

}
