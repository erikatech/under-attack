package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.Ingrediente;

public interface IngredienteDao {
	
	void salva(Ingrediente ingrediente);
	
	List<Ingrediente> todos();
	
	Ingrediente consulta(Long id);
	
}
