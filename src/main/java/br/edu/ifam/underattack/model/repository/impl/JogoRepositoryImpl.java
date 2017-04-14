package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.FaseDao;
import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.Objetivo;
import br.edu.ifam.underattack.model.repository.JogoRepository;

public class JogoRepositoryImpl implements JogoRepository {

	private FaseDao faseDAO;
	
	@Inject
	public JogoRepositoryImpl(FaseDao faseDAO) {
		this.faseDAO = faseDAO;
	}

	/** Apenas para o CDI */
	JogoRepositoryImpl() {
		this(null);
	}

	@Override
	public void adiciona(Fase fase) {
		this.faseDAO.adiciona(fase);
	}
	
	@Override
	public List<Fase> todasAsFases() {
		return this.faseDAO.todasAsFases();
	}

	@Override
	public void adiciona(Objetivo objetivo) {
		this.faseDAO.adiciona(objetivo);
	}
}
