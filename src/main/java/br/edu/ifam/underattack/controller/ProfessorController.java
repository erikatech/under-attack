package br.edu.ifam.underattack.controller;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.edu.ifam.underattack.dao.ProfessorDao;
import br.edu.ifam.underattack.interceptor.annotations.BugsEncontrados;
import br.edu.ifam.underattack.interceptor.annotations.Public;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.Professor;
import br.edu.ifam.underattack.model.repository.ProfessorRepository;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@Controller
@Path("/professor")
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
	@Consumes(value = "application/json", options = WithoutRoot.class)
	@Post("/register")
	public void adiciona(Professor professor) {
		dao.adiciona(professor);
		result.use(Results.status()).ok();
	}

	@Public
	@Consumes(value = "application/json")
	@Post("")
	public void login(String login, String senha) {
		Professor professor = dao.consulta(login, senha);
		validator.addIf(professor == null, new SimpleMessage("invalid", "Login ou senha inválidos"));
		validator.onErrorSendBadRequest();
		result.use(Results.status()).ok();
	}

	@Public
	@Get("")
	public void check(String login) {
		Professor professor = dao.findByLogin(login);
		boolean isLoginAvailable = professor == null;
		result.use(Results.json()).from(isLoginAvailable).serialize();
	}

}
