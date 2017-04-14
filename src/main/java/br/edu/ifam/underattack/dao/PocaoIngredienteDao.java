package br.edu.ifam.underattack.dao;

import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;

public interface PocaoIngredienteDao {

	void associa(PocaoMagicaIngrediente pocaoIngrediente);

	void atualiza(PocaoMagicaIngrediente pocaoIngrediente);

}
