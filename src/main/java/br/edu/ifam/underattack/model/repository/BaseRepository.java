package br.edu.ifam.underattack.model.repository;

import br.edu.ifam.underattack.model.Programa;


public interface BaseRepository {
	Programa consulta(Long id);

}
