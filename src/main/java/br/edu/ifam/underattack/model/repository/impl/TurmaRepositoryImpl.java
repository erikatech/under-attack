package br.edu.ifam.underattack.model.repository.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.AlunoDao;
import br.edu.ifam.underattack.dao.FaseDao;
import br.edu.ifam.underattack.dao.NivelAlunoDao;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.FaseObjetivo;
import br.edu.ifam.underattack.model.Objetivo;
import br.edu.ifam.underattack.model.PocaoMagica;
import br.edu.ifam.underattack.model.enums.SituacaoFase;
import br.edu.ifam.underattack.model.repository.TurmaRepository;

public class TurmaRepositoryImpl implements TurmaRepository {

	private AlunoDao alunoDao;
	
	private FaseDao faseDao;

	private NivelAlunoDao nivelDao;

	@Inject
	public TurmaRepositoryImpl(AlunoDao dao, FaseDao faseDao, NivelAlunoDao nivelDao) {
		this.alunoDao = dao;
		this.faseDao = faseDao;
		this.nivelDao = nivelDao;
	}

	/** Apenas para o CDI */
	TurmaRepositoryImpl() {
		this(null, null,null);
	}

	@Override
	public void adiciona(Aluno aluno) {
		Fase salaTestadores = faseDao.todasAsFases().get(0);
		Fase salaDesenvolvedores = faseDao.todasAsFases().get(1);
		
		// Cada fase é associada ao aluno, assumindo as características iniciais
		AlunoParticipaFase alunoParticipaSalaTestadores = new AlunoParticipaFase();
		alunoParticipaSalaTestadores.setAluno(aluno);
		alunoParticipaSalaTestadores.setFase(salaTestadores);
		alunoParticipaSalaTestadores.setHistoriaExibida(false);
		alunoParticipaSalaTestadores.setSituacaoFase(SituacaoFase.ESPERA);
		
		Set<FaseObjetivo> objetivosSalaTestadores = new HashSet<FaseObjetivo>();
		List<Objetivo> desafiosSalaTestadores = this.faseDao.todosOsDesafios(1L);
		for (Objetivo objetivo : desafiosSalaTestadores) {
			FaseObjetivo obj = new FaseObjetivo();
			obj.setAlunoFase(alunoParticipaSalaTestadores);
			obj.setObjetivo(objetivo);
			obj.setRealizado(false);
			objetivosSalaTestadores.add(obj);
		}
		alunoParticipaSalaTestadores.setFaseObjetivo(objetivosSalaTestadores);
		

		// Configurando a sala dos desenvolvedores
		AlunoParticipaFase alunoParticipaSalaDesenvolvedores = new AlunoParticipaFase();
		alunoParticipaSalaDesenvolvedores.setAluno(aluno);
		alunoParticipaSalaDesenvolvedores.setFase(salaDesenvolvedores);
		alunoParticipaSalaDesenvolvedores.setHistoriaExibida(false);
		alunoParticipaSalaDesenvolvedores.setSituacaoFase(SituacaoFase.BLOQUEADA);
		
		Set<FaseObjetivo> objetivosSalaDesenvolvedores = new HashSet<FaseObjetivo>();
		List<Objetivo> desafiosSalaDesenvolvedores = this.faseDao.todosOsDesafios(2L);
		for (Objetivo objetivo : desafiosSalaDesenvolvedores) {
			FaseObjetivo obj = new FaseObjetivo();
			obj.setAlunoFase(alunoParticipaSalaDesenvolvedores);
			obj.setObjetivo(objetivo);
			obj.setRealizado(false);
			objetivosSalaDesenvolvedores.add(obj);
		}
		
		alunoParticipaSalaDesenvolvedores.setFaseObjetivo(objetivosSalaDesenvolvedores);
		
		List<AlunoParticipaFase> alunoParticipaFase = Arrays.asList(alunoParticipaSalaTestadores, alunoParticipaSalaDesenvolvedores);
		
		aluno.setAlunoParticipaFase(alunoParticipaFase);
		aluno.setPocaoMagica(new PocaoMagica());
		aluno.setNivelAluno(nivelDao.consulta(1L));

		alunoDao.adiciona(aluno);
		
		
	}
	
	@Override
	public void atualiza(Aluno aluno) {
		this.alunoDao.atualiza(aluno);
	}

	@Override
	public Aluno consulta(String identificador, String senha) {
		return this.alunoDao.consulta(identificador, senha);
	}

	@Override
	public boolean existeAlunoComLogin(String login) {
		return this.alunoDao.existeAlunoComLogin(login);
	}

	@Override
	public boolean emailEmUtilizacao(String email) {
		return this.alunoDao.emailEmUtilizacao(email);
	}

	@Override
	public Aluno consulta(Long id) {
		return this.alunoDao.consulta(id);
	}
	
	
}
