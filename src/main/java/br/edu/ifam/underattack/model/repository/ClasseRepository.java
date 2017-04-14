package br.edu.ifam.underattack.model.repository;

import br.edu.ifam.underattack.model.Classe;


public interface ClasseRepository {
	Classe consulta(Long id);
	
	void salva(Classe classe);

}
