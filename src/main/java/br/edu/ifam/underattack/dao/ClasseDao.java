package br.edu.ifam.underattack.dao;

import br.edu.ifam.underattack.model.Classe;

/**
 * Data Access Object of Aluno entity.
 * 
 * @author Erika Silva
 */
public interface ClasseDAO {

	void adiciona(Classe classe);
	
	Classe consulta(Long id);


}
