package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.AlunoParticipaFase;

public interface AlunoFaseDAO {

	void adiciona(AlunoParticipaFase alunoParticipaFase);
	
	List<AlunoParticipaFase> todasAsFases(Long idAluno);
	
	AlunoParticipaFase consultaFaseAlunoPorId(Long idAlunoFase);
	
}
