package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.AlunoFaseDao;
import br.edu.ifam.underattack.dao.FaseDao;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoParticipaFase;
import br.edu.ifam.underattack.model.Fase;
import br.edu.ifam.underattack.model.enums.SituacaoFase;
import br.edu.ifam.underattack.model.repository.FaseRepository;

public class FaseRepositoryImpl implements FaseRepository {

	private AlunoFaseDao alunoFaseDao;

	private FaseDao faseDAO;

	@Inject
	public FaseRepositoryImpl(AlunoFaseDao alunoFaseDao, FaseDao faseDAO) {
		this.alunoFaseDao = alunoFaseDao;
		this.faseDAO = faseDAO;
	}

	/** Apenas para o CDI */
	FaseRepositoryImpl() {
		this(null, null);
	}

	@Override
	public void associa(Aluno aluno) {
		Fase salaTestadores = faseDAO.todasAsFases().get(0);
		Fase salaDesenvolvedores = faseDAO.todasAsFases().get(1);
		
		// Cada fase é associada ao aluno, assumindo as características iniciais

		AlunoParticipaFase alunoParticipaSalaTestadores = new AlunoParticipaFase();
		alunoParticipaSalaTestadores.setAluno(aluno);
		alunoParticipaSalaTestadores.setFase(salaTestadores);
		alunoParticipaSalaTestadores.setHistoriaExibida(false);
		alunoParticipaSalaTestadores.setSituacaoFase(SituacaoFase.ESPERA);

		AlunoParticipaFase alunoParticipaSalaDesenvolvedores = new AlunoParticipaFase();
		alunoParticipaSalaDesenvolvedores.setAluno(aluno);
		alunoParticipaSalaDesenvolvedores.setFase(salaDesenvolvedores);
		alunoParticipaSalaDesenvolvedores.setHistoriaExibida(false);
		alunoParticipaSalaDesenvolvedores
				.setSituacaoFase(SituacaoFase.BLOQUEADA);

		alunoFaseDao.adiciona(alunoParticipaSalaTestadores);
		alunoFaseDao.adiciona(alunoParticipaSalaDesenvolvedores);
	}
	
	@Override
	public List<AlunoParticipaFase> todasAsFases(Long idAluno) {
		return this.alunoFaseDao.todasAsFases(idAluno);
	}
	
	@Override
	public AlunoParticipaFase consultaFasePorId(Long idAlunoFase) {
		return this.alunoFaseDao.consultaFaseAlunoPorId(idAlunoFase);
	}

}
