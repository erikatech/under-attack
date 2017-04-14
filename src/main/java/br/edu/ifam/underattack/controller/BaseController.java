package br.edu.ifam.underattack.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.edu.ifam.underattack.interceptor.annotations.Public;
import br.edu.ifam.underattack.model.CasoTeste;
import br.edu.ifam.underattack.model.Classe;
import br.edu.ifam.underattack.model.ClasseEquivalencia;
import br.edu.ifam.underattack.model.Desafio;
import br.edu.ifam.underattack.model.Documento;
import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.Objetivo;
import br.edu.ifam.underattack.model.Programa;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.Dificuldade;
import br.edu.ifam.underattack.model.enums.NivelDesafio;
import br.edu.ifam.underattack.model.enums.TipoClasseEquivalencia;
import br.edu.ifam.underattack.model.enums.TipoObjetivo;
import br.edu.ifam.underattack.model.enums.TipoValorEntrada;
import br.edu.ifam.underattack.model.repository.ArmarioRepository;
import br.edu.ifam.underattack.model.repository.BaseRepository;
import br.edu.ifam.underattack.model.repository.ClasseRepository;
import br.edu.ifam.underattack.model.repository.JogoRepository;

@Controller
public class BaseController {
	
	private JogoRepository jogo;
	
	private ArmarioRepository armario;
	
	private ClasseRepository classeRepository;
	
	private BaseRepository baseRepository;
	
	@Inject
	public BaseController(JogoRepository jogo,  ArmarioRepository armario, ClasseRepository classeRepository,
			BaseRepository baseRepository) {
		this.jogo = jogo;
		this.armario = armario;
		this.classeRepository = classeRepository;
		this.baseRepository = baseRepository;
				
	}
	
	/**
	 * @deprecated CDI eyes only
	 */
	BaseController() {
	}
	
	@Public
	public void form(){}
	
	@Public
	public void classes(){};
	

	@Public
	public void adiciona() {
		Documento documento = new Documento();
		documento.setTitulo("Identificador Válido");
		documento.setValoresEntrada(this.buildListaValoresEntrada());
		
		Programa programa = new Programa();
		
		programa.setDocumento(documento);
		
		Desafio desafio = new Desafio();
		desafio.setTitulo("Função Identificador Válido");
		desafio.setNivel(NivelDesafio.FACIL);
		desafio.setPrograma(programa);
		desafio.setClasseCSS("caveira");
		
		List<Desafio> desafios = new ArrayList<Desafio>();
		desafios.add(desafio);
		
		Fase faseSalaTestadores = new Fase();
		faseSalaTestadores.setTitulo("Sala dos Testadores");
		faseSalaTestadores.setDesafios(desafios);
		faseSalaTestadores.setImagem(buildImagemBytes("C:\\Users\\Erikaa\\Dropbox\\TCC-1\\Material\\Documentos Monografia\\Modelagem II\\gambster.png"));
		
		Objetivo encontrarTodosItens = new Objetivo();
		encontrarTodosItens.setDescricao("Encontrar os itens da poção");
		encontrarTodosItens.setFase(faseSalaTestadores);
		encontrarTodosItens.setPontos(100);
		encontrarTodosItens.setTipoObjetivo(TipoObjetivo.POCAO);
		
		Objetivo encontrarTodosZombugs = new Objetivo();
		encontrarTodosZombugs.setDescricao("Encontrar todos os zombugs");
		encontrarTodosZombugs.setFase(faseSalaTestadores);
		encontrarTodosZombugs.setPontos(250);
		encontrarTodosZombugs.setTipoObjetivo(TipoObjetivo.ZOMBUGS);
		
		Set<Objetivo> objetivosSalaTestadores = new HashSet<Objetivo>(Arrays.asList(encontrarTodosItens,encontrarTodosZombugs));
		
		faseSalaTestadores.setObjetivos(objetivosSalaTestadores);
		
		// Configurando sala dos desenvolvedores
		Fase faseSalaDesenvolvedores = new Fase();
		faseSalaDesenvolvedores.setTitulo("Sala dos Desenvolvedores");
		faseSalaDesenvolvedores.setImagem(buildImagemBytes("C:\\Users\\Erikaa\\Dropbox\\TCC-1\\Material\\Documentos Monografia\\Modelagem II\\zombie-hand.png"));
		
		Objetivo preencherMaquinaComTestes = new Objetivo();
		preencherMaquinaComTestes.setDescricao("Energizar a máquina com testes");
		preencherMaquinaComTestes.setFase(faseSalaDesenvolvedores);
		preencherMaquinaComTestes.setPontos(250);
		preencherMaquinaComTestes.setTipoObjetivo(TipoObjetivo.MAQUINA);
		
		Set<Objetivo> objetivosSalaDesenvolvedores =  new HashSet<Objetivo>(Arrays.asList(preencherMaquinaComTestes));
		
		faseSalaDesenvolvedores.setObjetivos(objetivosSalaDesenvolvedores);
		
		this.jogo.adiciona(faseSalaTestadores);
		this.jogo.adiciona(faseSalaDesenvolvedores);
		
	}
	
	@Public
	public void atualizaPrograma() {
		
		Classe classe = new Classe();
		
		classe.setNome("ValidadorUtil");
		
		StringBuilder codigoBuilder = new StringBuilder();
		codigoBuilder
			.append("public class ValidadorUtil { " )
			.append("public boolean isQuantidadeValorCorreto(String identificador){ ")
			.append("String identificadorSemEspacosEmBranco = identificador.replaceAll(\"\\s\", \"\");")
			.append("return identificadorSemEspacosEmBranco.length() >= 4 && identificadorSemEspacosEmBranco.length() >= 6;")
			.append("}")
			.append("public boolean validarTipoDoValor(String identificador){ ")
			.append("String expressaoRegular = \"^(?=.*[a-zA-Z])(?=.*[\\d])(?=.*\\W).+$\";")
			.append("return identificador.matches(expressaoRegular);")
			.append("}")
			.append("public boolean validarTamanhoDoValor(String identificador){")
			.append("String expressaoRegular = \"^(?=.*[\\d]{3,})\\S.+$\";")
			.append("return identificador.matches(expressaoRegular);")
			.append("}")
			.append("}");
		
		classe.setCodigo(codigoBuilder.toString());
		Programa programa = this.baseRepository.consulta(1L);
		classe.setPrograma(programa);
		classeRepository.salva(classe);
		
	}
	
	private List<ValorDeEntrada> buildListaValoresEntrada(){
		ValorDeEntrada quantidadeValores = new ValorDeEntrada("Quantidade de Valores (QV)", TipoValorEntrada.CORRETO);
		quantidadeValores.setDificuldade(Dificuldade.FACIL);
		
		ClasseEquivalencia quantidadeValorIgualAQuatro = new ClasseEquivalencia(
				"QV = 4", "QV = 4", false, TipoClasseEquivalencia.VALIDA);
		quantidadeValorIgualAQuatro.setDificuldade(Dificuldade.FACIL);
		
		CasoTeste casoTeste = new CasoTeste();
		casoTeste.setResultadoEsperado("Identificador Válido");
		casoTeste.setSaida("Identificador Válido");
		quantidadeValorIgualAQuatro.setCasoTeste(casoTeste);
		
		ClasseEquivalencia quantidadeValorMaiorQueQuatro = new ClasseEquivalencia(
				"QV > 4", "QV > 4", false, TipoClasseEquivalencia.VALIDA);
		quantidadeValorMaiorQueQuatro.setDificuldade(Dificuldade.NORMAL);
		
		CasoTeste casoTeste2 = new CasoTeste();
		casoTeste2.setResultadoEsperado("Identificador Válido");
		casoTeste2.setSaida("Identificador Válido");
		quantidadeValorMaiorQueQuatro.setCasoTeste(casoTeste2);
		
		ClasseEquivalencia quantidadeValorMenorQueQuatro = new ClasseEquivalencia(
				"QV < 4", "QV < 4", false,
				TipoClasseEquivalencia.INVALIDA);
		quantidadeValorMenorQueQuatro.setDificuldade(Dificuldade.NORMAL);
		CasoTeste casoTeste3 = new CasoTeste();
		casoTeste3.setResultadoEsperado("Identificador Inválido");
		casoTeste3.setSaida("Identificador Inválido");
		quantidadeValorMenorQueQuatro.setCasoTeste(casoTeste3);
		
		ClasseEquivalencia quantidadeValorIgualASeis = new ClasseEquivalencia(
				"QV = 6", "QV = 6", false,
				TipoClasseEquivalencia.VALIDA);
		quantidadeValorIgualASeis.setDificuldade(Dificuldade.NORMAL);
		CasoTeste casoTeste4 = new CasoTeste();
		casoTeste4.setResultadoEsperado("Identificador Válido");
		casoTeste4.setSaida("Identificador Válido");
		quantidadeValorIgualASeis.setCasoTeste(casoTeste4);		
		
		ClasseEquivalencia quantidadeValorMaiorQueSeis = new ClasseEquivalencia(
				"QV > 6", "QV > 6", true,
				TipoClasseEquivalencia.INVALIDA);
		quantidadeValorMaiorQueSeis.setDificuldade(Dificuldade.NORMAL);
		
		CasoTeste casoTeste5 = new CasoTeste();
		casoTeste5.setResultadoEsperado("Identificador Inválido");
		casoTeste5.setSaida("Identificador Válido");
		quantidadeValorMaiorQueSeis.setCasoTeste(casoTeste5);
		
		quantidadeValores.adicionarClasseEquivalencia(quantidadeValorIgualAQuatro);
		quantidadeValores.adicionarClasseEquivalencia(quantidadeValorMaiorQueQuatro);
		quantidadeValores.adicionarClasseEquivalencia(quantidadeValorMenorQueQuatro);
		quantidadeValores.adicionarClasseEquivalencia(quantidadeValorIgualASeis);
		quantidadeValores.adicionarClasseEquivalencia(quantidadeValorMaiorQueSeis);
		
		
		ValorDeEntrada tipoValor = new ValorDeEntrada("Tipo do Valor (TPV)",
				TipoValorEntrada.CORRETO);
		tipoValor.setDificuldade(Dificuldade.NORMAL);
		
		ClasseEquivalencia tipovalorNaoEInteiro = new ClasseEquivalencia(
				"TPV não é inteiro", "não inteiro", true,
				TipoClasseEquivalencia.INVALIDA);
		tipovalorNaoEInteiro.setDificuldade(Dificuldade.NORMAL);
		CasoTeste casoTeste7 = new CasoTeste();
		casoTeste7.setResultadoEsperado("Identificador Inválido");
		casoTeste7.setSaida("Identificador Inválido");
		tipovalorNaoEInteiro.setCasoTeste(casoTeste7);
		
		tipoValor.adicionarClasseEquivalencia(tipovalorNaoEInteiro);
		
		ValorDeEntrada tamanhoValor = new ValorDeEntrada("Tamanho do Valor (TV)",
				TipoValorEntrada.CORRETO);
		tamanhoValor.setDificuldade(Dificuldade.DIFICIL);
		
		// TODO Consultar ItemPocao e adicionar
		ClasseEquivalencia tamanhoDoValorMaiorQueDois = new ClasseEquivalencia(
				"TV > 2", "TV > 2", false,
				TipoClasseEquivalencia.INVALIDA);
		tamanhoDoValorMaiorQueDois.setDificuldade(Dificuldade.DIFICIL);
		tamanhoDoValorMaiorQueDois.setIngrediente(this.armario.consulta(1L));
		
		CasoTeste casoTeste9 = new CasoTeste();
		casoTeste9.setResultadoEsperado("Identificador Inválido");
		casoTeste9.setSaida("Identificador Inválido");
		tamanhoDoValorMaiorQueDois.setCasoTeste(casoTeste9);
		
		ClasseEquivalencia tamanhoDoValorMenorQueDois = new ClasseEquivalencia(
				"TV < 2", "TV < 2", true,
				TipoClasseEquivalencia.INVALIDA);
		tamanhoDoValorMenorQueDois.setDificuldade(Dificuldade.DIFICIL);
		
		CasoTeste casoTeste10 = new CasoTeste();
		casoTeste10.setResultadoEsperado("Identificador Inválido");
		casoTeste10.setSaida("Identificador Válido");
		tamanhoDoValorMenorQueDois.setCasoTeste(casoTeste10);
		
		tamanhoValor.adicionarClasseEquivalencia(tamanhoDoValorMaiorQueDois);
		tamanhoValor.adicionarClasseEquivalencia(tamanhoDoValorMenorQueDois);
		
		ValorDeEntrada distrativo1 = new ValorDeEntrada("Capacidade de Armazenamento (CA)",
				TipoValorEntrada.DISTRATIVO);
		ValorDeEntrada distrativo2 = new ValorDeEntrada("Digitos Inteiros (DI)",
				TipoValorEntrada.DISTRATIVO);
		ValorDeEntrada distrativo3 = new ValorDeEntrada("Caracteres Especiais (CE)",
				TipoValorEntrada.DISTRATIVO);
		ValorDeEntrada distrativo4 = new ValorDeEntrada("Apenas Alfanuméricos (AA)",
				TipoValorEntrada.DISTRATIVO);
		
		List<ValorDeEntrada> valoresEntrada = new ArrayList<ValorDeEntrada>();
		
		valoresEntrada.add(quantidadeValores);
		valoresEntrada.add(tipoValor);
		valoresEntrada.add(tamanhoValor);
		valoresEntrada.add(distrativo1);
		valoresEntrada.add(distrativo2);
		valoresEntrada.add(distrativo3);
		valoresEntrada.add(distrativo4);
		
		return valoresEntrada;
		
	}
	
	private byte[] buildImagemBytes(String path) {
		File file = new File(path);
		byte[] imageData = new byte[(int) file.length()];

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(imageData);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageData;
	}

}
