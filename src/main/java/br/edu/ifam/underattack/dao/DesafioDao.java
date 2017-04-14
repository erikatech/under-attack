package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.Desafio;

public interface DesafioDAO {

	List<Desafio> listaTodos();
	
	Desafio buscaPorId(Long id);
	
	void atualiza(Desafio desafio);

}
