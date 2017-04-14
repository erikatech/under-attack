package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoParticipaFase;

public interface FaseRepository {
	
	void associa(Aluno aluno);
	
	List<AlunoParticipaFase> todasAsFases(Long idAluno);
	
	AlunoParticipaFase consultaFasePorId(Long idAlunoFase);
	

}
