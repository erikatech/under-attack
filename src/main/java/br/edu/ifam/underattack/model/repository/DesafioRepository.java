package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.AlunoEncontraClasseEquivalencia;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.enums.IndicadorFase;


public interface DesafioRepository {
	
	void associa(Long idAluno, Long idDesafio, IndicadorFase indicadorFase);
	
	Boolean alunoRealizouDesafio(Long idAluno, Long idDesafio, IndicadorFase indicadorFase);
	
	AlunoRealizaDesafio buscaDesafioDoAluno(Long idAluno, Long idDesafio, IndicadorFase indicadorFase);
	
	AlunoRealizaDesafio consulta(Long id);
	
	void atualiza(AlunoRealizaDesafio alunoDesafio);
	
	void encontraClasseEquivalencia(AlunoEncontraClasseEquivalencia alunoClasse);
	
	List<AlunoEncontraClasseEquivalencia> todos(Long idAluno);
	

}
