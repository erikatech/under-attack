package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.ImagemIngrediente;
import br.edu.ifam.underattack.model.Ingrediente;

public interface IngredienteDAO {
	
	void salva(Ingrediente ingrediente);
	
	List<Ingrediente> todos();
	
	Ingrediente consulta(Long id);
	
	ImagemIngrediente buscarImagemPorTipo(Ingrediente ingrediente);
	
}
