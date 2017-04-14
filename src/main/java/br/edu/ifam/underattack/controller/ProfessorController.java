package br.edu.ifam.underattack.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.edu.ifam.underattack.dao.ProfessorDao;
import br.edu.ifam.underattack.interceptor.annotations.BugsEncontrados;
import br.edu.ifam.underattack.interceptor.annotations.Public;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.Professor;
import br.edu.ifam.underattack.model.repository.ProfessorRepository;

import javax.inject.Inject;
import javax.persistence.NoResultException;

@Controller
public class ProfessorController {

	private final Result result;

	private ProfessorDao dao;

	private final Validator validator;

	@Inject
	public ProfessorController(ProfessorDao dao, Result result, Validator validator) {
		this.dao = dao;
		this.result = result;
		this.validator = validator;
	}

	/** Apenas para o CDI
	 * */
	ProfessorController() {
		this(null, null, null);
	}

	@Public
	@Post
	public void adiciona(Professor professor) {
		dao.adiciona(professor);
		result.include("sucesso", "Professor cadastrado com sucesso");
	}

	@Public
	@Post("/admin-login")
	public void login(String login, String senha) throws NoResultException{
		dao.consulta(login, senha);
		/*validator.addIf(professorConsultado == null, new I18nMessage("login", "login.senha.invalidos"));

		validator.onErrorUsePageOf(this).login();*/

//		alunoInfo.login(alunoConsultado);
//		result.redirectTo(this).home();
	}

}
