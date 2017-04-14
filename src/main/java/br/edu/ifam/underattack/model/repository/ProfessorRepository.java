package br.edu.ifam.underattack.model.repository;

import br.edu.ifam.underattack.model.Professor;

public interface ProfessorRepository {

	void adiciona(Professor professor);

	Professor consulta(Long id);
	

}
