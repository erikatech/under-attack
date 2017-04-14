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

import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.edu.ifam.underattack.controller.HomeController;
import br.edu.ifam.underattack.interceptor.annotations.Public;

/**
 * Interceptador que verifica se o usuário está logado
 */
@Intercepts
public class AutenticacaoInterceptor {

	private final AlunoInfo alunoInfo;
	private final Result result;

	@Inject
	public AutenticacaoInterceptor(AlunoInfo info, 
			Result result) {
		this.alunoInfo = info;
		this.result = result;
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	public AutenticacaoInterceptor() {
		this(null, null);
	}

	/**
	 * Irá interceptar apenas aqueles cujo acesso não for publico
	 * */
	@Accepts
	public boolean accepts(ControllerMethod method) {
		return !method.containsAnnotation(Public.class);
	}

	/**
	 * Intercepta o request e verifica se o usuário está logado
	 */
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		if (this.alunoInfo.isLogado()){
			stack.next();
		} else {
			result.redirectTo(HomeController.class).login();
		}
	}

}
