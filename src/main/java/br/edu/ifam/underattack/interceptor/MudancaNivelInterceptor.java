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
import br.edu.ifam.underattack.interceptor.annotations.MudancaNivel;
import br.edu.ifam.underattack.model.NivelAluno;
import br.edu.ifam.underattack.model.repository.NivelAlunoRepository;
import br.edu.ifam.underattack.model.repository.TurmaRepository;

/**
 * Interceptador que verifica se o usuário está logado
 */
@Intercepts(after=AutenticacaoInterceptor.class)
public class MudancaNivelInterceptor {

	private final AlunoInfo alunoInfo;
	private final TurmaRepository turmaRepository;
	private final NivelAlunoRepository nivelRepository;

	@Inject
	public MudancaNivelInterceptor(AlunoInfo info, 
			TurmaRepository turmaRepository,NivelAlunoRepository nivelRepository) {
		this.alunoInfo = info;
		this.turmaRepository = turmaRepository;
		this.nivelRepository = nivelRepository;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public MudancaNivelInterceptor() {
		this(null, null,null);
	}

	/**
	 * Irá interceptar apenas aqueles cujo acesso não for publico
	 * */
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(MudancaNivel.class);
	}

	/**
	 * Intercepta o request e verifica se o usuário está logado
	 */
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		int pontuacaoAluno = this.alunoInfo.getAluno().getPontos();
		NivelAluno nivelAluno = this.alunoInfo.getAluno().getNivelAluno();
	
		NivelAluno novoNivel = null;
		
		List<NivelAluno> todosOsNiveis = this.nivelRepository.todosOsNiveis();
		
		for (NivelAluno nivel : todosOsNiveis) {
			if (pontuacaoAluno >= nivel.getMinimo() && pontuacaoAluno <= nivel.getMaximo() && !nivel.getId().equals(nivelAluno.getId())) {
				novoNivel = nivel;
				break;
			}
		}
		if (novoNivel != null) {
			this.alunoInfo.getAluno().setNivelAluno(novoNivel);
			this.turmaRepository.atualiza(this.alunoInfo.getAluno());
			this.alunoInfo.setMudouDeNivel(true);
		} else {
			this.alunoInfo.setMudouDeNivel(false);
		} 
		stack.next();
	}

}
