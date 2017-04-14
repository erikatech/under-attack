package br.edu.ifam.underattack.dao;

import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.enums.IndicadorFase;

public interface AlunoDesafioDAO {

	void associa(AlunoRealizaDesafio alunoRealizaDesafio);
	
	AlunoRealizaDesafio buscaDesafioDoAluno(Long idAluno, Long idDesafio,IndicadorFase indicadorFase);
	
	Boolean alunoRealizouDesafio(Long idAluno, Long idDesafio, IndicadorFase indicadorFase);

	AlunoRealizaDesafio consulta(Long id);

	void atualiza(AlunoRealizaDesafio alunoDesafio);
	
}
