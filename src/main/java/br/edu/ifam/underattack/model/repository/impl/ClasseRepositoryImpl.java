package br.edu.ifam.underattack.model.repository.impl;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.ClasseDao;
import br.edu.ifam.underattack.model.Classe;
import br.edu.ifam.underattack.model.repository.ClasseRepository;

public class ClasseRepositoryImpl implements ClasseRepository {

	private ClasseDao classeDao;


	@Inject
	public ClasseRepositoryImpl(ClasseDao classeDao) {
		this.classeDao = classeDao;
	}

	/** Apenas para o CDI */
	ClasseRepositoryImpl() {
		this(null);
	}

	@Override
	public Classe consulta(Long id) {
		return this.classeDao.consulta(id);
	}

	@Override
	public void salva(Classe classe) {
		this.classeDao.adiciona(classe);
	}

	


	

}
