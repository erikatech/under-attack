package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.IngredienteDao;
import br.edu.ifam.underattack.dao.PocaoIngredienteDao;
import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;
import br.edu.ifam.underattack.model.repository.ArmarioRepository;

public class ArmarioRepositoryImpl implements ArmarioRepository {
	
	private IngredienteDao ingredienteDao;
	
	private PocaoIngredienteDao pocaoIngrediente;
	
	@Inject
	public ArmarioRepositoryImpl(IngredienteDao dao, PocaoIngredienteDao pocaoIngrediente ) {
		this.ingredienteDao = dao;
		this.pocaoIngrediente = pocaoIngrediente;
	}
	
	/** Apenas para o CDI*/
	ArmarioRepositoryImpl (){  this(null, null); }

	@Override
	public void armazena(Ingrediente ingrediente) {
		this.ingredienteDao.salva(ingrediente);
	}

	@Override
	public List<Ingrediente> todosOsIngredientes() {
		return this.ingredienteDao.todos();
	}
	
	@Override
	public Ingrediente consulta(Long id) {
		return ingredienteDao.consulta(id);
	}
	
	@Override
	public void atualizaPocaoIngrediente(PocaoMagicaIngrediente pocaoIngrediente) {
		this.pocaoIngrediente.atualiza(pocaoIngrediente);
	}

}
