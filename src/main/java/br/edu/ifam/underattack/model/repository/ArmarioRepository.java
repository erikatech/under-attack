package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;

public interface ArmarioRepository {

	void armazena(Ingrediente ingrediente);
	
	List<Ingrediente> todosOsIngredientes();
	
	Ingrediente consulta(Long id);
	
	void atualizaPocaoIngrediente(PocaoMagicaIngrediente pocaoIngrediente);

}
