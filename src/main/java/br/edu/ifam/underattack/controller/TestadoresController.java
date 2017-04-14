package br.edu.ifam.underattack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.edu.ifam.underattack.interceptor.AlunoInfo;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoEncontraClasseEquivalencia;
import br.edu.ifam.underattack.model.AlunoEncontraValorDeEntrada;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.ClasseEquivalencia;
import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.model.PocaoMagicaIngrediente;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.IndicadorFase;
import br.edu.ifam.underattack.model.enums.SituacaoDesafio;
import br.edu.ifam.underattack.model.enums.SituacaoIngrediente;
import br.edu.ifam.underattack.model.repository.ArmarioRepository;
import br.edu.ifam.underattack.model.repository.DesafioRepository;
import br.edu.ifam.underattack.model.repository.FaseRepository;
import br.edu.ifam.underattack.model.repository.TurmaRepository;
import br.edu.ifam.underattack.model.repository.ValorDeEntradaRepository;
import br.edu.ifam.underattack.to.RetornoClasseEquivalenciaTO;

@Controller
public class TestadoresController {

	private TurmaRepository turmaRepository;

	private DesafioRepository desafioRepository;

	private ValorDeEntradaRepository valorEntradaRepository;

	private ArmarioRepository armarioRepository;
	
	private FaseRepository faseRepository;
	
	private Result result;

	private HttpServletRequest request;

	private AlunoInfo alunoInfo;

	private Validator validator;
	
	@Inject
	public TestadoresController(TurmaRepository turmaRepository, final Result result,FaseRepository faseRepository,
			HttpServletRequest request, DesafioRepository desafio,
			ValorDeEntradaRepository valorEntradaRepository,
			ArmarioRepository armarioRepository, AlunoInfo alunoInfo,
			Validator validator) {
		this.turmaRepository = turmaRepository;
		this.result = result;
		this.faseRepository = faseRepository;
		this.request = request;
		this.desafioRepository = desafio;
		this.valorEntradaRepository = valorEntradaRepository;
		this.armarioRepository = armarioRepository;
		this.alunoInfo = alunoInfo;
		this.validator = validator;
	}

	/**
	 * @deprecated CDI eyes only
	 */
	TestadoresController() {
	}

	public void iniciaDesafio(Long idAluno, Long idDesafio) {
		AlunoRealizaDesafio alunoRealizaDesafio =  this.desafioRepository.buscaDesafioDoAluno(idAluno, idDesafio,IndicadorFase.FASE_TESTADORES);
		
		if (alunoRealizaDesafio == null) {
			this.desafioRepository.associa(idAluno, idDesafio,IndicadorFase.FASE_TESTADORES);
			alunoRealizaDesafio = this.desafioRepository.buscaDesafioDoAluno(idAluno, idDesafio,IndicadorFase.FASE_TESTADORES);
		} else if (null != alunoRealizaDesafio.getAluno().getAlunoEncontraValorDeEntrada() 
				&& !alunoRealizaDesafio.getAluno().getAlunoEncontraValorDeEntrada().isEmpty()) {
			this.alunoInfo.iniciaClassesEquivalencia(alunoRealizaDesafio);
			this.result.redirectTo(this).classes();
			return;
		}
		this.alunoInfo.iniciaValorEntrada(alunoRealizaDesafio);
		this.result.redirectTo(this).valores();
		
	}

	@Post("/valida")
	public void validaValorEntrada(String id) {
		Long idValorEntrada = Long.valueOf(id);

		ValorDeEntrada valorEscolhido = this.valorEntradaRepository
				.consulta(idValorEntrada);

		AlunoRealizaDesafio alunoDesafio = this.alunoInfo.getAlunoDesafio();
		List<Long> idsValoresCorretos = new ArrayList<Long>();

		for (ValorDeEntrada valorDeEntrada : this.alunoInfo.getAlunoDesafio()
				.getDesafio().getPrograma().getDocumento().getValoresEntrada()) {
			if (valorDeEntrada.isValorEntradaCorreto()) {
				idsValoresCorretos.add(valorDeEntrada.getId());
			}
		}

		if (idsValoresCorretos.contains(idValorEntrada)) {
			this.result.use(Results.http())
					.body(String.valueOf(valorEscolhido.getDificuldade()
							.getPontos()));
		} else {
			alunoDesafio.setCerebrosDisponiveis(alunoDesafio
					.getCerebrosDisponiveis() - 1);
			this.result.use(Results.http()).body("errado");

		}
		// Armazeno os dados na sess�o
		AlunoEncontraValorDeEntrada alunoValorDeEntrada = new AlunoEncontraValorDeEntrada();
		alunoValorDeEntrada.setAluno(alunoDesafio.getAluno());
		alunoValorDeEntrada.setValorDeEntrada(valorEscolhido);
		this.alunoInfo.getAlunoDesafio().getAluno()
				.adicionaValorEntrada(alunoValorDeEntrada);
	}

	/**
	 * Atualiza os valores de entrada e pontua��o do aluno no banco
	 * */
	public void salvarValoresEntrada() {
		List<AlunoEncontraValorDeEntrada> valoresEncontrados = this.alunoInfo
				.getAlunoDesafio().getAluno().getAlunoEncontraValorDeEntrada();

		this.validator.addIf(!encontrouValorCorreto(valoresEncontrados),
				new I18nMessage("valor", "valor.entrada.obrigatorio"));

		validator.onErrorRedirectTo(this).valores();

		AlunoRealizaDesafio alunoDesafio = this.alunoInfo.getAlunoDesafio();

		for (AlunoEncontraValorDeEntrada valor : valoresEncontrados) {
			this.valorEntradaRepository.adiciona(valor);
			if (valor.getValorDeEntrada().isValorEntradaCorreto()) {
				int pontos = valor.getValorDeEntrada().getDificuldade() == null ? alunoDesafio
						.getAluno().getPontos() : alunoDesafio.getAluno()
						.getPontos()
						+ valor.getValorDeEntrada().getDificuldade()
								.getPontos();
				this.alunoInfo.getAluno().setPontos(this.alunoInfo.getAluno().getPontos() + pontos);
			}
		}
		this.turmaRepository.atualiza(this.alunoInfo.getAluno());
		this.result.redirectTo(this).classes();
	}

	/**
	 * */
	public boolean encontrouValorCorreto(
			List<AlunoEncontraValorDeEntrada> valoresEncontrados) {
		int quantidade = 0;
		for (AlunoEncontraValorDeEntrada alunoEncontraValorDeEntrada : valoresEncontrados) {
			if (alunoEncontraValorDeEntrada.getValorDeEntrada()
					.isValorEntradaCorreto()) {
				quantidade++;
			}
		}

		return quantidade > 0;
	}

	/**
	 * Valida as classes de equivalencia que foram enviadas na requisi��o
	 * 
	 * */
	@Get("/validaClasse")
	public void validaClassesEquivalencia() {
		RetornoClasseEquivalenciaTO retornoClasseEquivalencia = new RetornoClasseEquivalenciaTO();
		
		String[] entradas = request.getParameterValues("entradas[]");
		String[] idsValorEntrada = request.getParameterValues("idsValorEntrada[]");

		Aluno aluno = this.alunoInfo.getAluno();

		validator.addIf(this.nenhumCampoPreenchido(entradas), new I18nMessage(
				"preencha.campo", "preecha.pelo.menos.um.campo"));
		validator.addIf(this.maisDeUmCampoPreenchido(entradas),
				new I18nMessage("apenas.um.campo", "preencha.apenas.um.campo"));
		validator.onErrorSendBadRequest();

		String entradaAluno = null;
		Long idValorEntrada = null;

		for (int i = 0; i < entradas.length; i++) {
			if (!entradas[i].trim().equals("")) {
				entradaAluno = entradas[i];
				idValorEntrada = Long.valueOf(idsValorEntrada[i]);
				break;
			}
		}

		ValorDeEntrada valorEntrada = this.valorEntradaRepository.consulta(idValorEntrada);
		List<ClasseEquivalencia> classesDoValorDeEntrada = valorEntrada.getClassesEquivalencia();
		
		for (ClasseEquivalencia classeEquivalencia : classesDoValorDeEntrada) {
			if (entradaAluno.matches(classeEquivalencia.getExpressaoRegular())) {
				CopyOnWriteArrayList<AlunoEncontraClasseEquivalencia> alunoClasseCopy = 
						new CopyOnWriteArrayList<AlunoEncontraClasseEquivalencia>(this.alunoInfo.getAlunoDesafio().getAluno().getAlunoEncontraClasseEquivalencia());
				
				if (alunoClasseCopy.isEmpty()) {
					retornoClasseEquivalencia.setEncontrouClasse(true);
					retornoClasseEquivalencia.setRepetiuClasse(false);
					
					AlunoEncontraClasseEquivalencia alunoClasseEquivalencia = new AlunoEncontraClasseEquivalencia();
					alunoClasseEquivalencia.setAluno(aluno);
					alunoClasseEquivalencia
							.setClasseEquivalencia(classeEquivalencia);
					alunoClasseEquivalencia.setEntradaAluno(entradaAluno);
					this.alunoInfo.getAlunoDesafio().getAluno()
							.adicionarClasseEquivalencia(alunoClasseEquivalencia);
				} else {
					List<Long> idsClassesEquivalencia = new ArrayList<Long>();
					for (AlunoEncontraClasseEquivalencia alunoEncontraClasseEquivalencia : alunoClasseCopy) {
						idsClassesEquivalencia.add(alunoEncontraClasseEquivalencia.getClasseEquivalencia().getId());
					}
					
					if (!idsClassesEquivalencia.contains(classeEquivalencia.getId())) {
						retornoClasseEquivalencia.setRepetiuClasse(false);
						retornoClasseEquivalencia.setEncontrouClasse(true);
						
						AlunoEncontraClasseEquivalencia alunoClasseEquivalencia = new AlunoEncontraClasseEquivalencia();
						alunoClasseEquivalencia.setAluno(aluno);
						alunoClasseEquivalencia
								.setClasseEquivalencia(classeEquivalencia);
						alunoClasseEquivalencia.setEntradaAluno(entradaAluno);
						this.alunoInfo.getAlunoDesafio().getAluno()
								.adicionarClasseEquivalencia(alunoClasseEquivalencia);
						alunoClasseCopy = 
								new CopyOnWriteArrayList<AlunoEncontraClasseEquivalencia>(this.alunoInfo.getAlunoDesafio().getAluno().getAlunoEncontraClasseEquivalencia());
					} else {
						retornoClasseEquivalencia.setRepetiuClasse(true);
						retornoClasseEquivalencia.setEncontrouClasse(false);
					}
				}
			}
		}
		if (retornoClasseEquivalencia.isEncontrouClasse()) {
			this.result
				.use(Results.json())
				.from(this.alunoInfo.getAlunoDesafio().getAluno()
						.getAlunoEncontraClasseEquivalencia())
				.include("classeEquivalencia")
				.include("classeEquivalencia.valorDeEntrada")
				.include("classeEquivalencia.casoTeste")
				.include("classeEquivalencia.ingrediente").serialize();
		} else {
			this.alunoInfo.getAlunoDesafio().setCerebrosDisponiveis(alunoInfo.getAlunoDesafio().getCerebrosDisponiveis() - 1);
			this.result.use(Results.json()).from(retornoClasseEquivalencia).serialize();
		}
	}

	/**
	 * consulta a imagem do ingrediente
	 * 
	 * */
	@Get("/consultaImagemIngrediente")
	public void consultaImagemIngrediente() {
		String idIngrediente = request.getParameter("id");

		Ingrediente ingrediente = this.armarioRepository.consulta(Long
				.valueOf(idIngrediente));

		this.result.use(Results.json()).from(ingrediente.getNomeImagem()).serialize();
	}

	/**
	 * Verifica se mais de um campo foi preenchido
	 * */
	private boolean maisDeUmCampoPreenchido(String[] entradasAluno) {
		int camposPreenchidos = 0;
		for (String entrada : entradasAluno) {
			if (!entrada.trim().isEmpty()) {
				camposPreenchidos++;
			}
		}
		return camposPreenchidos > 1;
	}

	/**
	 * Verifica se nenhum campo foi preenchido
	 * 
	 * */
	private boolean nenhumCampoPreenchido(String[] entradasAluno) {
		int camposVazios = 0;
		for (String entrada : entradasAluno) {
			if (entrada.trim().isEmpty()) {
				camposVazios++;
			}
		}
		return camposVazios == entradasAluno.length;
	}

	/**
	 * Armazena as informa��es no banco
	 * 
	 * */
	@Post("/finaliza")
	public void finaliza() {
		int bugsEncontrados = 0;
		for (AlunoEncontraClasseEquivalencia classe : this.alunoInfo.getAlunoDesafio().getAluno().getAlunoEncontraClasseEquivalencia()) {
			
			this.desafioRepository.encontraClasseEquivalencia(classe);
			this.alunoInfo.getAluno().setPontos(this.alunoInfo.getAluno().getPontos()+ classe.getClasseEquivalencia().getDificuldade()
									.getPontos());

			if (classe.getClasseEquivalencia().hasItemPocao()) {
				for (PocaoMagicaIngrediente pocaoIngrediente : this.alunoInfo
						.getAluno().getPocaoMagica().getPocaoIngredienteList()) {
					if (pocaoIngrediente
							.getIngrediente()
							.getId()
							.equals(classe.getClasseEquivalencia()
									.getIngrediente().getId())) {
						pocaoIngrediente
								.setSituacaoIngrediente(SituacaoIngrediente.ENCONTRADO);
						this.armarioRepository
								.atualizaPocaoIngrediente(pocaoIngrediente);
						break;
					}
				}
			}
			
			
			if (classe.getClasseEquivalencia().getBugExistente()) {
				bugsEncontrados++;
			}
		}
		
		//Atualiza a quantidade de bugs encontrados
		AlunoParticipaFase faseTestadores = this.alunoInfo.getAluno().getAlunoParticipaFase().get(0);
		int encontradosAtual = faseTestadores.getBugsEncontrados();
		faseTestadores.setBugsEncontrados(encontradosAtual + bugsEncontrados);
		
		AlunoRealizaDesafio alunoDesafio = this.alunoInfo.getAlunoDesafio();	
		alunoDesafio.getAluno().getAlunoParticipaFase().get(0).setBugsEncontrados(bugsEncontrados);		
		alunoDesafio.setSituacaoDesafio(SituacaoDesafio.CONCLUIDO);
		alunoDesafio.setDesempenho(alunoDesafio.calcularDesempenho());

		desafioRepository.atualiza(alunoDesafio);

		this.turmaRepository.atualiza(this.alunoInfo.getAluno());
		this.alunoInfo.getAlunoDesafio().setDesempenho(
				alunoInfo.getAlunoDesafio().calcularDesempenho());
		this.alunoInfo.getAlunoDesafio().setTotalEstrelas(
				this.alunoInfo.getAlunoDesafio().getDesempenho()
						.getTotalEstrelas());

		this.result
				.use(Results.json())
				.from(this.alunoInfo.getAlunoDesafio())
				.include("aluno")
				.include("aluno.alunoEncontraValorDeEntrada")
				.include("aluno.alunoEncontraClasseEquivalencia")
				.include(
						"aluno.alunoEncontraClasseEquivalencia.classeEquivalencia")
				.include("desafio")
				.include("desafio.programa")
				.include("desafio.programa.documento")
				.include("desafio.programa.documento.valoresEntrada")
				.include(
						"desafio.programa.documento.valoresEntrada.classesEquivalencia")
				.serialize();
		
		/*this.alunoInfo.reinicarValoresEncontrados();
		this.alunoInfo.reinicarClassesEquivalencia();*/
		this.alunoInfo.conclui();
		this.atualizaAlunoSessao();

	}
	
	private void atualizaAlunoSessao(){
		this.alunoInfo.getAluno().setAlunoParticipaFase(this.faseRepository.todasAsFases(this.alunoInfo.getAluno().getId()));
	}

	@Get("/testadores/desafio/classes")
	public void classes() {
	}

	@Path("/testadores/desafio/valores")
	public void valores() {
	}

	/**
	 * */
	public void tentarNovamenteValoresEntrada() {
		this.alunoInfo.getAlunoDesafio().setCerebrosDisponiveis(3);
		this.alunoInfo.reinicarValoresEncontrados();
		this.result.redirectTo(this).valores();

	}

	/**
	 * */
	public void tentarNovamenteClassesEquivalencia() {
		this.alunoInfo.getAlunoDesafio().setCerebrosDisponiveis(3);
		this.alunoInfo.getAlunoDesafio().getAluno().getAlunoEncontraClasseEquivalencia().clear();
		this.result.redirectTo(this).classes();
	}

}
