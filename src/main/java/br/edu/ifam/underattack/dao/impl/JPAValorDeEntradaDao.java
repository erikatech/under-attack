package br.edu.ifam.underattack.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.edu.ifam.underattack.dao.ValorDeEntradaDAO;
import br.edu.ifam.underattack.model.AlunoEncontraValorDeEntrada;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.TipoValorEntrada;

@Transactional
public class JPAValorDeEntradaDAO implements ValorDeEntradaDAO {

	private final EntityManager manager;

	@Inject
	public JPAValorDeEntradaDAO(EntityManager manager) {
		this.manager = manager;
	}

	JPAValorDeEntradaDAO() {
		this(null);
	}

	@Override
	public List<ValorDeEntrada> listaPorTipo(Long idDocumento, TipoValorEntrada tipo) {
		TypedQuery<ValorDeEntrada> query = manager.createQuery(
				"select v from ValorDeEntrada v where v.documento.id =:idDocumento and v.tipo=:tipo",
				ValorDeEntrada.class);
		query.setParameter("idDocumento", idDocumento);
		query.setParameter("tipo", tipo);
		return query.getResultList();
	}

	@Override
	public Boolean valorDeEntradaEncontrado(Long idAluno, Long idDocumento) {
		return !valoresEncontrados(idAluno, idDocumento).isEmpty();
	}
	
	@Override
	public void atualiza(AlunoEncontraValorDeEntrada valorDeEntrada) {
		this.manager.merge(valorDeEntrada);
	}

	@Override
	public ValorDeEntrada consulta(Long id) {
		return this.manager.find(ValorDeEntrada.class, id);
	}
	
	@Override
	public List<AlunoEncontraValorDeEntrada> valoresEncontrados(Long idAluno,
			Long idDocumento) {
		TypedQuery<AlunoEncontraValorDeEntrada> query = manager
				.createQuery(
						"select av from AlunoEncontraValorDeEntrada av where av.aluno.id=:idAluno and av.valorDeEntrada.documento.id=:idDocumento",
						AlunoEncontraValorDeEntrada.class);
		query.setParameter("idAluno", idAluno);
		query.setParameter("idDocumento", idDocumento);
		return query.getResultList();
	}

	@Override
	public void adiciona(AlunoEncontraValorDeEntrada valorDeEntrada) {
		this.manager.persist(valorDeEntrada);
	}

}
