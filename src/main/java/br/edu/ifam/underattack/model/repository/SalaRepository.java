package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.Desafio;

public interface SalaRepository {

	List<Desafio> todosOsDesafios();
	
	Desafio consulta(Long id);

	void atualiza(Desafio desafio);
	

}
