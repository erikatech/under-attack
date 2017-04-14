package br.edu.ifam.underattack.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.edu.ifam.underattack.interceptor.annotations.BugsEncontrados;
import br.edu.ifam.underattack.interceptor.annotations.Public;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.repository.FrascoRepository;
import br.edu.ifam.underattack.model.repository.TurmaRepository;

@Controller
public class AlunoController {

	private TurmaRepository turma;

	private FrascoRepository frasco;

	private Result result;

	private Validator validator;

	@Inject
	public AlunoController(TurmaRepository turma, FrascoRepository frasco, Result result, Validator validator) {
		this.turma = turma;
		this.frasco = frasco;
		this.result = result;
		this.validator = validator;
	}

	/** Apenas para o CDI */
	AlunoController() {
	}

	@Public
	@Post
	public void adiciona(@Valid Aluno aluno) {
		
		validator.addIf(turma.existeAlunoComLogin(aluno.getLogin()), new I18nMessage("aluno.login", "login_indisponivel"));
		
		validator.addIf(turma.emailEmUtilizacao(aluno.getEmail()), new I18nMessage("aluno.email", "email.ja.cadastrado"));
		
		validator.onErrorRedirectTo(HomeController.class).cadastrar();

		turma.adiciona(aluno);

		frasco.associaIngredientes(aluno.getPocaoMagica());

		result.include("sucesso", "Aluno cadastrado com sucesso");
		
		result.redirectTo(HomeController.class).login();
	}
	
	@Get("/fases")
	@BugsEncontrados
	public void home() {
		
	}



}
