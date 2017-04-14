package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.NivelAluno;

public interface NivelAlunoRepository {

	NivelAluno consulta(Long id);
	
	List<NivelAluno> todosOsNiveis();
	

}
