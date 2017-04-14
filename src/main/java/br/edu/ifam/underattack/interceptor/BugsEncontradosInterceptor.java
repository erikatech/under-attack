/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.edu.ifam.underattack.interceptor;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.edu.ifam.underattack.interceptor.annotations.BugsEncontrados;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.FaseObjetivo;
import br.edu.ifam.underattack.model.enums.TipoObjetivo;
import br.edu.ifam.underattack.model.repository.TurmaRepository;

/**
 * Interceptador que verifica se o usuário está logado
 */
@Intercepts(before = AutenticacaoInterceptor.class)
public class BugsEncontradosInterceptor {

	private final AlunoInfo alunoInfo;
	private final TurmaRepository turmaRepository;
	

	@Inject
	public BugsEncontradosInterceptor(AlunoInfo info, 
			TurmaRepository turmaRepository) {
		this.alunoInfo = info;
		this.turmaRepository = turmaRepository;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public BugsEncontradosInterceptor() {
		this(null, null);
	}

	/**
	 * Irá interceptar apenas aqueles metodos que estiverem com essa anotação
	 * */
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(BugsEncontrados.class) && this.alunoInfo.getAluno() != null;
	}

	/**
	 * Intercepta o request e verifica se o aluno encontrou todos os zombugs da fase
	 */
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		// Verificação se o aluno encontrou todos os zombugs na Sala dos Testadores
		AlunoParticipaFase alunoParticipaFaseTestadores = this.alunoInfo.getAluno().getAlunoParticipaFase().get(0);
		int totalBugsEncontrados = alunoParticipaFaseTestadores.getBugsEncontrados();
		int totalBugsDaFase = alunoParticipaFaseTestadores.getFase().getQuantidadeBugsFase();
		if (totalBugsEncontrados == totalBugsDaFase) {
			FaseObjetivo objetivoRealizado = alunoParticipaFaseTestadores.getObjetivoRealizado(TipoObjetivo.ZOMBUGS);
			objetivoRealizado.setRealizado(true);
			this.alunoInfo.getAluno().setPontos(this.alunoInfo.getAluno().getPontos() + objetivoRealizado.getObjetivo().getPontos());
		}
		
		//Verifico se escreveu todos os testes da fase, significa que a máquina foi preenchida
		AlunoParticipaFase alunoParticipaFaseDesenvolvedores = this.alunoInfo.getAluno().getAlunoParticipaFase().get(1);
		int quantidadeCasosTeste = alunoParticipaFaseDesenvolvedores.getFase().getQuantidadeCasosTeste();
		int totalTestesEscritos = 0;
		
		List<AlunoRealizaDesafio> alunoRealizaDesafio = alunoParticipaFaseDesenvolvedores.getAluno().getAlunoRealizaDesafio();
		for (AlunoRealizaDesafio alunoDesafio : alunoRealizaDesafio) {
			totalTestesEscritos += alunoDesafio.getTotalTestesEscritos();
		}
		
		if (quantidadeCasosTeste == totalTestesEscritos) {
			FaseObjetivo objetivoRealizado = alunoParticipaFaseDesenvolvedores.getObjetivoRealizado(TipoObjetivo.MAQUINA);
			objetivoRealizado.setRealizado(true);
			this.alunoInfo.getAluno().setPontos(this.alunoInfo.getAluno().getPontos() + objetivoRealizado.getObjetivo().getPontos());
		}
		this.turmaRepository.atualiza(this.alunoInfo.getAluno());
		stack.next();
	}

}
