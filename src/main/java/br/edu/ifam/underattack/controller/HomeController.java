package br.edu.ifam.underattack.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.edu.ifam.underattack.dao.AlunoDao;
import br.edu.ifam.underattack.interceptor.AlunoInfo;
import br.edu.ifam.underattack.interceptor.annotations.Public;
import br.edu.ifam.underattack.model.Aluno;

@Controller
public class HomeController {
	
	private final Result result;
	private final Validator validator;
	private final AlunoInfo alunoInfo;
	private final AlunoDao dao;

	/**
	 * @deprecated CDI eyes only
	 */
	protected HomeController() {
		this(null, null, null, null);
	}
	
	@Inject
	public HomeController(AlunoDao dao, AlunoInfo alunoInfo, Result result, Validator validator) {
	    this.dao = dao;
		this.result = result;
	    this.validator = validator;
        this.alunoInfo = alunoInfo;
	}

	@Public
	@Post("/login")
	public void login(String login, String senha) {
		final Aluno alunoConsultado = dao.consulta(login, senha);

		validator.addIf(alunoConsultado == null, new I18nMessage("login", "login.senha.invalidos"));
		
		validator.onErrorUsePageOf(this).login();
		
		alunoInfo.login(alunoConsultado);

		result.redirectTo(AlunoController.class).home();
	}
	
	public void logout() {
	    alunoInfo.logout();
	    result.redirectTo(this).login();
	}
	
	@Public
	@Get("/login")
	public void login() {
		
	}
	
	@Public
	@Get("/cadastrar")
	public void cadastrar() {}
	

}
