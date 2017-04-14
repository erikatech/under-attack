package br.edu.ifam.underattack.model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.IngredienteDao;
import br.edu.ifam.underattack.dao.PocaoMagicaDao;
import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.model.PocaoMagica;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;
import br.edu.ifam.underattack.model.enums.SituacaoIngrediente;
import br.edu.ifam.underattack.model.repository.FrascoRepository;

public class FrascoRepositoryImpl implements FrascoRepository {

	private PocaoMagicaDao pocaoDAO;

	private IngredienteDao ingredienteDao;
	
	@Inject
	public FrascoRepositoryImpl(PocaoMagicaDao pocaoDAO,
			IngredienteDao ingredienteDao) {
		this.pocaoDAO = pocaoDAO;
		this.ingredienteDao = ingredienteDao;
	}

	/** Apenas para o CDI */
	FrascoRepositoryImpl() {
		this(null, null);
	}


	@Override
	public void associaIngredientes(PocaoMagica pocaoMagica) {
		List<PocaoMagicaIngrediente> pocaoMagicaIngredienteList = new ArrayList<PocaoMagicaIngrediente>();
		List<Ingrediente> ingredientes = ingredienteDao.todos();
		for (Ingrediente ingrediente : ingredientes) {
			PocaoMagicaIngrediente pocaoIngrediente = new PocaoMagicaIngrediente();
			pocaoIngrediente.setIngrediente(ingrediente);
			pocaoIngrediente.setPocaoMagica(pocaoMagica);
			pocaoIngrediente
					.setSituacaoIngrediente(SituacaoIngrediente.ESCONDIDO);
			pocaoMagicaIngredienteList.add(pocaoIngrediente);
		}
		pocaoMagica.setPocaoIngredienteList(pocaoMagicaIngredienteList);
		pocaoDAO.atualiza(pocaoMagica);
	}
}
