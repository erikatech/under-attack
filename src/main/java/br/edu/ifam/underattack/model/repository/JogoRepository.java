package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.Objetivo;

public interface JogoRepository {

	void adiciona(Fase fase);
	
	List<Fase> todasAsFases();
	
	void adiciona(Objetivo objetivo);
	
}
