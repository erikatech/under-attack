package br.edu.ifam.underattack.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import junit.framework.TestCase;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.edu.ifam.underattack.compiler.CompilerException;
import br.edu.ifam.underattack.compiler.JavaCodeCompiler;
import br.edu.ifam.underattack.interceptor.AlunoInfo;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.Classe;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.IndicadorFase;
import br.edu.ifam.underattack.model.enums.SituacaoDesafio;
import br.edu.ifam.underattack.model.repository.ClasseRepository;
import br.edu.ifam.underattack.model.repository.DesafioRepository;
import br.edu.ifam.underattack.model.repository.FaseRepository;
import br.edu.ifam.underattack.model.repository.TurmaRepository;
import br.edu.ifam.underattack.to.ResultadoTestesTO;
import br.edu.ifam.underattack.to.RetornoTesteTO;
import br.edu.ifam.underattack.to.TestesExceptionsTO;

@Controller
public class DesenvolvedoresController {

	private Result result;

	private DesafioRepository desafioRepository;

	private AlunoInfo alunoInfo;

	private HttpServletRequest request;

	private ClasseRepository classeRepository;

	private TurmaRepository turmaRepository;
	
	private Validator validator;

	private FaseRepository faseRepository;

	@Inject
	public DesenvolvedoresController(Result result,
			ClasseRepository classeRepository,TurmaRepository turmaRepository,
			DesafioRepository desafioRepository, AlunoInfo alunoInfo,
			HttpServletRequest request,Validator validator,FaseRepository faseRepository) {
		this.result = result;
		this.classeRepository = classeRepository;
		this.turmaRepository = turmaRepository;
		this.desafioRepository = desafioRepository;
		this.alunoInfo = alunoInfo;		
		this.request = request;
		this.validator = validator;
		this.faseRepository = faseRepository;
	}

	/**
	 * @deprecated CDI eyes only
	 */
	DesenvolvedoresController() {
	}

	public void iniciaDesafio(Long idAluno, Long idDesafio) {
		AlunoRealizaDesafio alunoDesafio = this.desafioRepository.buscaDesafioDoAluno(idAluno, idDesafio,IndicadorFase.FASE_DESENVOLVEDORES);
		
		if (null == alunoDesafio) {
			this.desafioRepository.associa(idAluno, idDesafio,IndicadorFase.FASE_DESENVOLVEDORES);
			alunoDesafio = this.desafioRepository.buscaDesafioDoAluno(idAluno, idDesafio,IndicadorFase.FASE_DESENVOLVEDORES);
		} 
		this.alunoInfo.iniciaClasses(alunoDesafio);
		this.result.redirectTo(this).testes();
	}

	@Get("/desenvolvedores/desafio/testes")
	public void testes() {};

	@Get("/validarTestes")
	public void validar() {
		TestesExceptionsTO exceptionTO = new TestesExceptionsTO();
		String codigo = request.getParameter("codigo");
		String nomeClasse = request.getParameter("nomeClasse");
		
		this.alunoInfo.setListaResultados(new ArrayList<RetornoTesteTO>());
		
		this.validator.addIf(nomeClasse.trim().isEmpty(), new I18nMessage("classe", "preencher.nome.classe"));
		
		validator.onErrorSendBadRequest();
		
		try {
			StringBuilder codigoBuilder = new StringBuilder();
			codigoBuilder
				.append("import static org.junit.Assert.assertEquals;\n")
				.append("import org.junit.Test;")
				.append("import br.edu.ifam.underattack.suporte.*;")
				.append(codigo);
			
			JavaCodeCompiler<TestCase> compiler = new JavaCodeCompiler<TestCase>();
			
			Class<TestCase> teste = compiler.compile(null, nomeClasse, codigoBuilder.toString());
			
			org.junit.runner.Result result = JUnitCore.runClasses(teste);
			
			List<RetornoTesteTO> listaResultados = new ArrayList<RetornoTesteTO>();
			
			for (Failure falha : result.getFailures()) {
				RetornoTesteTO retornoTeste = new RetornoTesteTO();
				String descricaoErro = falha.toString().replace('<', ' ').replace('>', ' ');
				retornoTeste.setDescricao(descricaoErro);
				retornoTeste.setErro(Boolean.TRUE);
				retornoTeste.setNomeMetodo(falha.getDescription().getMethodName());
				listaResultados.add(retornoTeste);
			}
			
			Method[] metodos = teste.getDeclaredMethods();
			List<Method> metodosCopy = new CopyOnWriteArrayList<Method>(metodos);
			
			for (Method method : metodos) {
				for (RetornoTesteTO resultado : listaResultados) {
					if (method.getName().equals(resultado.getNomeMetodo())) {
						metodosCopy.remove(method);
					}
				}
			}
			
			for (Method method : metodosCopy) {
				RetornoTesteTO retornoTeste = new RetornoTesteTO();
				retornoTeste.setDescricao(method.getName());
				retornoTeste.setErro(Boolean.FALSE);
				listaResultados.add(retornoTeste);
			}
				
			int totalTestesEscritos = this.alunoInfo.getAlunoDesafio().getTotalTestesEscritos();
			this.alunoInfo.getAlunoDesafio().setTotalTestesEscritos(totalTestesEscritos + listaResultados.size());
			this.alunoInfo.setListaResultados(listaResultados);			
			this.result.use(Results.json()).from(listaResultados).serialize();
		} catch (CompilerException compilerException) {
			StringBuilder exceptionBuilder = new StringBuilder();
			List<Diagnostic<? extends JavaFileObject>> diagnostics = compilerException.getDiagnostics().getDiagnostics();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
				exceptionBuilder.append(diagnostic.toString()).append("<br/>");
			}
			exceptionTO.setDescricaoErro(exceptionBuilder.toString());
			this.result.use(Results.json()).from(exceptionTO).serialize();
		} catch (ClassFormatError classFormatException){
			exceptionTO.setDescricaoErro(classFormatException.toString());
			this.result.use(Results.json()).from(exceptionTO).serialize();
		}
	}
	
	/**
	 * Armazena as informações no banco
	 * 
	 * */
	@Post("/finalizarTestes")
	public void finalizarTestes() {
		AlunoRealizaDesafio desafioAtual = this.alunoInfo.getAlunoDesafio();
		
		int pontuacaoFinal = 0;
		List<RetornoTesteTO> resultadosAluno = this.alunoInfo.getResultadosTeste();
		
		/** Soma os pontos */
		for (RetornoTesteTO retornoTesteTO : resultadosAluno) {
			if(retornoTesteTO.isErro()){
				pontuacaoFinal += retornoTesteTO.getPontos();
			}
		}
		int totalTestesDoDesafio = 0;
		List<ValorDeEntrada> valoresEntrada = desafioAtual.getDesafio().getPrograma().getDocumento().getValoresEntrada();
		for (ValorDeEntrada valorDeEntrada : valoresEntrada) {
			if (valorDeEntrada.isValorEntradaCorreto()) {
				totalTestesDoDesafio += valorDeEntrada.getClassesEquivalencia().size();
			}
		}
		/** Soma a quantidade de testes escritos com os testes do banco */
		int totalTestesEscritos = desafioAtual.getTotalTestesEscritos();	
		desafioAtual.setTotalTestesEscritos(totalTestesEscritos);
		
		int pontosAluno = this.alunoInfo.getAluno().getPontos();
		this.alunoInfo.getAluno().setPontos(pontosAluno + pontuacaoFinal);

		desafioAtual.setSituacaoDesafio(SituacaoDesafio.CONCLUIDO);
		
		desafioAtual.setDesempenho(desafioAtual.calcularDesempenhoEscritaTestes(resultadosAluno.size()));

		desafioAtual.setTotalEstrelas(desafioAtual.getDesempenho().getTotalEstrelas());
		
		/** Atualiza as informações no banco */
		desafioRepository.atualiza(desafioAtual);
		turmaRepository.atualiza(this.alunoInfo.getAluno());

		ResultadoTestesTO resultado = new ResultadoTestesTO();
		resultado.setQuantidadeEstrelas(desafioAtual.getDesempenho().getTotalEstrelas());
		resultado.setTestesEscritos(resultadosAluno.size());
		resultado.setPontuacaoFinal(pontuacaoFinal);
		resultado.setTotalTestes(totalTestesDoDesafio);
		
		this.atualizaAlunoSessao();
		this.alunoInfo.reiniciaFaseDesenvolvedores();
		this.alunoInfo.conclui();
		
		this.result
				.use(Results.json())
				.from(resultado)
				.serialize();
	}
	
	private void atualizaAlunoSessao(){
		this.alunoInfo.getAluno().setAlunoParticipaFase(this.faseRepository.todasAsFases(this.alunoInfo.getAluno().getId()));
	}
	
	
	/**
	 * Recupera a classe associada a um id
	 * 
	 * */
	@Get("/carregarClasse")
	public void carregarClasse() {
		String idClasse = request.getParameter("id");

		Classe classe = this.classeRepository.consulta(Long.valueOf(idClasse));

		this.result.use(Results.json()).from(classe.getCodigo()).serialize();
	}

}
