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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.edu.ifam.underattack.interceptor.annotations.DesbloqueioFase;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.FaseObjetivo;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;
import br.edu.ifam.underattack.model.enums.SituacaoFase;
import br.edu.ifam.underattack.model.enums.SituacaoIngrediente;
import br.edu.ifam.underattack.model.enums.TipoObjetivo;
import br.edu.ifam.underattack.model.repository.TurmaRepository;

/**
 * Interceptador que verifica se o usuário está logado
 */
@Intercepts(after = AutenticacaoInterceptor.class)
public class DesbloqueioFaseInterceptor {

	private final AlunoInfo alunoInfo;
	private final TurmaRepository turmaRepository;

	@Inject
	public DesbloqueioFaseInterceptor(AlunoInfo info, 
			TurmaRepository turmaRepository) {
		this.alunoInfo = info;
		this.turmaRepository = turmaRepository;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public DesbloqueioFaseInterceptor() {
		this(null, null);
	}

	/**
	 * Irá interceptar apenas aqueles cujo acesso não for publico
	 * */
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(DesbloqueioFase.class);
	}

	/**
	 * Intercepta o request e verifica se a fase dos desenvolvedores foi desbloqueada
	 */
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		Aluno alunoConsultado = turmaRepository.consulta(this.alunoInfo.getAluno().getId());
		List<PocaoMagicaIngrediente> totalIngredientes = alunoConsultado.getPocaoMagica().getPocaoIngredienteList();
		List<PocaoMagicaIngrediente> ingredientesEncontrados = new ArrayList<PocaoMagicaIngrediente>();
		
		for (PocaoMagicaIngrediente pocaoMagicaIngrediente : totalIngredientes) {
			if (pocaoMagicaIngrediente.getSituacaoIngrediente().equals(SituacaoIngrediente.ENCONTRADO)){
				ingredientesEncontrados.add(pocaoMagicaIngrediente);
			}
		}
		if (totalIngredientes.size() == ingredientesEncontrados.size() 
				&& this.alunoInfo.getAluno().getAlunoParticipaFase().get(1).getSituacaoFase().equals(SituacaoFase.BLOQUEADA)){ 
			this.alunoInfo.getAluno().getAlunoParticipaFase().get(1).setSituacaoFase(SituacaoFase.ESPERA);
			this.alunoInfo.setDesbloqueouFaseDesenvolvedores(true);
			
			//Quando o aluno desbloqueia a fase dos desenvolvedores, significa que construiu a poção,
			//então atualiza-se o objetivo cumprido.
			AlunoParticipaFase salaTestadores = this.alunoInfo.getAluno().getAlunoParticipaFase().get(0);
			FaseObjetivo objetivoRealizado = salaTestadores.getObjetivoRealizado(TipoObjetivo.POCAO);
			objetivoRealizado.setRealizado(true);
			this.alunoInfo.getAluno().setPontos(this.alunoInfo.getAluno().getPontos() + objetivoRealizado.getObjetivo().getPontos());
			turmaRepository.atualiza(this.alunoInfo.getAluno());
			
		} else {
			this.alunoInfo.setDesbloqueouFaseDesenvolvedores(false);
		}
		stack.next();
	}

}
