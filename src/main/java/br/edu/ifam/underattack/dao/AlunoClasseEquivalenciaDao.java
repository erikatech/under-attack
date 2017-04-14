package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.AlunoEncontraClasseEquivalencia;

public interface AlunoClasseEquivalenciaDao {

	void associa(AlunoEncontraClasseEquivalencia alunoEncontraClasseEquivalencia);
	
	List<AlunoEncontraClasseEquivalencia> todos(Long idAluno);
	
}
