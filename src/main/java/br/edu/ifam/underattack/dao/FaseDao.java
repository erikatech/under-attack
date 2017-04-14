package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.Objetivo;

public interface FaseDao {
	
	void adiciona(Fase fase);
	
	void adiciona(Objetivo objetivo);
	
	List<Fase> todasAsFases();
	
	List<Objetivo> todosOsDesafios(Long idFase);
	
}
