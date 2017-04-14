package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.NivelAlunoDao;
import br.edu.ifam.underattack.model.NivelAluno;
import br.edu.ifam.underattack.model.repository.NivelAlunoRepository;

public class NivelAlunoRepositoryImpl implements NivelAlunoRepository {

	private NivelAlunoDao nivelDao;
	

	@Inject
	public NivelAlunoRepositoryImpl(NivelAlunoDao nivelDao) {
		this.nivelDao = nivelDao;
	}

	/** Apenas para o CDI */
	NivelAlunoRepositoryImpl() {
		this(null);
	}

	@Override
	public NivelAluno consulta(Long id) {
		return this.nivelDao.consulta(id);
	}

	@Override
	public List<NivelAluno> todosOsNiveis() {
		return this.nivelDao.todosOsNiveis();
	}

	
	
}
