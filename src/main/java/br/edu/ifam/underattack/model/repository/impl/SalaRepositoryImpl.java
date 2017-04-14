package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.DesafioDao;
import br.edu.ifam.underattack.model.Desafio;
import br.edu.ifam.underattack.model.repository.SalaRepository;

public class SalaRepositoryImpl implements SalaRepository {
	
	private DesafioDao dao;
	
	@Inject
	public SalaRepositoryImpl(DesafioDao dao) {
		this.dao = dao;
	}
	
	/** Apenas para o CDI*/
	SalaRepositoryImpl (){  this(null); }
	
	@Override
	public List<Desafio> todosOsDesafios() {
		return this.dao.listaTodos();
	}

	@Override
	public Desafio consulta(Long id) {
		return this.dao.buscaPorId(id);
	}
	
	@Override
	public void atualiza(Desafio desafio) {
		this.dao.atualiza(desafio);
	}


}
