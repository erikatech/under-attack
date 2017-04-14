package br.edu.ifam.underattack.model.repository.impl;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.BaseDao;
import br.edu.ifam.underattack.model.Programa;
import br.edu.ifam.underattack.model.repository.BaseRepository;

public class BaseRepositoryImpl implements BaseRepository {

	private BaseDao baseDAO;


	@Inject
	public BaseRepositoryImpl(BaseDao baseDAO) {
		this.baseDAO = baseDAO;
	}

	/** Apenas para o CDI */
	BaseRepositoryImpl() {
		this(null);
	}
	
	@Override
	public Programa consulta(Long id) {
		return this.baseDAO.consulta(id);
	}
}
