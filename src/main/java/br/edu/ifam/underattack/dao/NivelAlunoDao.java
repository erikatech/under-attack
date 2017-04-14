package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.NivelAluno;

public interface NivelAlunoDao {
	
	List<NivelAluno> todosOsNiveis();
	
	NivelAluno consulta(Long id);
	
}
