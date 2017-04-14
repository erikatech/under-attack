package br.edu.ifam.underattack.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.AlunoDesafioDao;
import br.edu.ifam.underattack.model.AlunoRealizaDesafio;
import br.edu.ifam.underattack.model.enums.IndicadorFase;

@Transactional
public class JPAAlunoDesafioDao implements AlunoDesafioDao {

	private final EntityManager manager;

	@Inject
	public JPAAlunoDesafioDao(EntityManager manager) {
		this.manager = manager;
	}

	JPAAlunoDesafioDao() {
		this(null);
	}

	@Override
	public void associa(AlunoRealizaDesafio alunoRealizaDesafio) {
		this.manager.persist(alunoRealizaDesafio);
	}

	@Override
	public Boolean alunoRealizouDesafio(Long idAluno, Long idDesafio,IndicadorFase indicadorFase) {
		return this.buscaDesafioDoAluno(idAluno, idDesafio,indicadorFase) != null;
	}

	@Override
	public AlunoRealizaDesafio buscaDesafioDoAluno(Long idAluno, Long idDesafio,IndicadorFase indicadorFase) {
		TypedQuery<AlunoRealizaDesafio> query = this.manager
				.createQuery("select ad from AlunoRealizaDesafio ad where ad.aluno.id =:idAluno and ad.desafio.id =:idDesafio"
						+ " and ad.indicadorFase =:indicadorFase",
						AlunoRealizaDesafio.class);
		query.setParameter("idAluno", idAluno);
		query.setParameter("idDesafio", idDesafio);
		query.setParameter("indicadorFase", indicadorFase);
		try {
			AlunoRealizaDesafio alunoDesafio = query.getSingleResult();
			alunoDesafio.getDesafio().getPrograma().getDocumento().getValoresEntrada().size();
			alunoDesafio.getAluno().getAlunoEncontraValorDeEntrada().size();
			alunoDesafio.getAluno().getAlunoEncontraClasseEquivalencia().size();
			alunoDesafio.getDesafio().getPrograma().getClasses().size();
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AlunoRealizaDesafio consulta(Long id) {
		return this.manager.find(AlunoRealizaDesafio.class, id);
	}
	
	@Override
	public void atualiza(AlunoRealizaDesafio alunoDesafio) {
		this.manager.merge(alunoDesafio);
	}

}
