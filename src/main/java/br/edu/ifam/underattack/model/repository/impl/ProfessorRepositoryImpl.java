package br.edu.ifam.underattack.model.repository.impl;

import br.edu.ifam.underattack.dao.ProfessorDao;
import br.edu.ifam.underattack.model.Professor;
import br.edu.ifam.underattack.model.repository.ProfessorRepository;

import javax.inject.Inject;

public class ProfessorRepositoryImpl implements ProfessorRepository {

	private ProfessorDao professorDao;

	@Inject
	public ProfessorRepositoryImpl(ProfessorDao dao) {
		this.professorDao = dao;
	}

	/** Apenas para o CDI*/
	ProfessorRepositoryImpl(){  this(null); }

	@Override
	public void adiciona(Professor professor) {
		professorDao.adiciona(professor);
	}

	@Override
	public Professor consulta(Long id) {
		return professorDao.consulta(id);
	}
}
