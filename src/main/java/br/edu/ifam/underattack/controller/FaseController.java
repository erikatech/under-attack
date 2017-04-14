package br.edu.ifam.underattack.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.edu.ifam.underattack.interceptor.annotations.DesbloqueioFase;
import br.edu.ifam.underattack.interceptor.annotations.FimDeJogo;
import br.edu.ifam.underattack.interceptor.annotations.MudancaNivel;

@Controller
public class FaseController {
	
	/**
	 * @deprecated CDI eyes only
	 */
	FaseController() {
	}
	
	@Get("/fase/testadores")
	@MudancaNivel
	@DesbloqueioFase
	public void testadores() {}
	
	@Get("/fase/desenvolvedores")
	@FimDeJogo
	public void desenvolvedores() {}
	

}
