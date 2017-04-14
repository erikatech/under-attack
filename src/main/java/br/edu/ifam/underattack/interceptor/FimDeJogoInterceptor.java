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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.edu.ifam.underattack.interceptor.annotations.FimDeJogo;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;

/**
 * Interceptador que verifica se o usuário está logado
 */
@Intercepts(before = AutenticacaoInterceptor.class)
public class FimDeJogoInterceptor {

	private final AlunoInfo alunoInfo;
	

	@Inject
	public FimDeJogoInterceptor(AlunoInfo info) {
		this.alunoInfo = info;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public FimDeJogoInterceptor() {
		this(null);
	}

	/**
	 * Irá interceptar apenas aqueles metodos que estiverem com essa anotação
	 * */
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(FimDeJogo.class) && this.alunoInfo.getAluno() != null;
	}

	/**
	 * Intercepta o request e verifica se o aluno encontrou todos os zombugs da fase
	 */
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		//Verifico se escreveu todos os testes da fase, significa que a máquina foi preenchida
		AlunoParticipaFase alunoParticipaFaseDesenvolvedores = this.alunoInfo.getAluno().getAlunoParticipaFase().get(1);
		int quantidadeCasosTeste = alunoParticipaFaseDesenvolvedores.getFase().getQuantidadeCasosTeste();
		int totalTestesEscritos = 0;
		
		
		Set<AlunoRealizaDesafio> alunoRealizaDesafioSemRepetir = new HashSet<AlunoRealizaDesafio>();
		List<AlunoRealizaDesafio> alunoRealizaDesafio = alunoParticipaFaseDesenvolvedores.getAluno().getAlunoRealizaDesafio();

		for (AlunoRealizaDesafio ar : alunoRealizaDesafio) {
			alunoRealizaDesafioSemRepetir.add(ar);
		}
		
		for (AlunoRealizaDesafio alunoDesafio : alunoRealizaDesafioSemRepetir) {
			totalTestesEscritos += alunoDesafio.getTotalTestesEscritos();
		}
		
		if (totalTestesEscritos >= quantidadeCasosTeste) {
			this.alunoInfo.setFimJogo(true);
		} else {
			this.alunoInfo.setFimJogo(false);
		}
		stack.next();
	}

}
