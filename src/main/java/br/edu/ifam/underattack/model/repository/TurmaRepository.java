package br.edu.ifam.underattack.model.repository;

import br.edu.ifam.underattack.model.Aluno;

public interface TurmaRepository {

	void adiciona(Aluno aluno);
	
	void atualiza(Aluno aluno);
	
	Aluno consulta(String identificador, String senha);
	
	Aluno consulta(Long id);
	
	boolean existeAlunoComLogin(String login);
	
	boolean emailEmUtilizacao(String email);
	
	
	
}
