package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.AlunoClasseEquivalenciaDao;
import br.edu.ifam.underattack.dao.AlunoDao;
import br.edu.ifam.underattack.dao.AlunoDesafioDao;
import br.edu.ifam.underattack.dao.DesafioDao;
import br.edu.ifam.underattack.model.Aluno;
import br.edu.ifam.underattack.model.AlunoEncontraClasseEquivalencia;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.Desafio;
import br.edu.ifam.underattack.model.enums.IndicadorFase;
import br.edu.ifam.underattack.model.enums.SituacaoDesafio;
import br.edu.ifam.underattack.model.repository.DesafioRepository;

public class DesafioRepositoryImpl implements DesafioRepository {

	private AlunoDesafioDao alunoDesafioDao;
	
	private AlunoDao alunoDAO;
	
	private DesafioDao desafioDao;
	
	private AlunoClasseEquivalenciaDao alunoClasseDAO;


	@Inject
	public DesafioRepositoryImpl(AlunoDesafioDao alunoDesafioDao, AlunoDao alunoDAO, DesafioDao desafioDao, AlunoClasseEquivalenciaDao alunoClasseDAO) {
		this.alunoDesafioDao = alunoDesafioDao;
		this.alunoDAO = alunoDAO;
		this.desafioDao = desafioDao;
		this.alunoClasseDAO = alunoClasseDAO;
	}

	/** Apenas para o CDI */
	DesafioRepositoryImpl() {
		this(null, null, null,null);
	}

	@Override
	public void associa(Long idAluno, Long idDesafio,IndicadorFase indicadorFase) {
		Aluno aluno = this.alunoDAO.consulta(idAluno);
		Desafio desafio = this.desafioDao.buscaPorId(idDesafio);
		
		AlunoRealizaDesafio alunoRealizaDesafio = new AlunoRealizaDesafio();
		alunoRealizaDesafio.setAluno(aluno);
		alunoRealizaDesafio.setDesafio(desafio);
		alunoRealizaDesafio.setSituacaoDesafio(SituacaoDesafio.EM_ANDAMENTO);
		alunoRealizaDesafio.setCerebrosDisponiveis(3);
		alunoRealizaDesafio.setIndicadorFase(indicadorFase);
		
		this.alunoDesafioDao.associa(alunoRealizaDesafio);
	}
	
	@Override
	public Boolean alunoRealizouDesafio(Long idAluno, Long idDesafio,IndicadorFase indicadorFase) {
		return this.alunoDesafioDao.alunoRealizouDesafio(idAluno, idDesafio,indicadorFase);
	}
	
	@Override
	public AlunoRealizaDesafio buscaDesafioDoAluno(Long idAluno, Long idDesafio,IndicadorFase indicadorFase) {
		return this.alunoDesafioDao.buscaDesafioDoAluno(idAluno, idDesafio,indicadorFase);
	}
	
	@Override
	public AlunoRealizaDesafio consulta(Long id) {
		return this.alunoDesafioDao.consulta(id);
	}
	
	@Override
	public void atualiza(AlunoRealizaDesafio alunoDesafio) {
		this.alunoDesafioDao.atualiza(alunoDesafio);
	}

	@Override
	public void encontraClasseEquivalencia(
			AlunoEncontraClasseEquivalencia alunoClasse) {
		this.alunoClasseDAO.associa(alunoClasse);
	}

	@Override
	public List<AlunoEncontraClasseEquivalencia> todos(Long idAluno) {
		return this.alunoClasseDAO.todos(idAluno);
	}

	

}
