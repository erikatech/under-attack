package br.edu.ifam.underattack.model.repository.impl;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.underattack.dao.ValorDeEntradaDao;
import br.edu.ifam.underattack.model.AlunoEncontraValorDeEntrada;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.TipoValorEntrada;
import br.edu.ifam.underattack.model.repository.ValorDeEntradaRepository;

public class ValorDeEntradaRepositoryImpl implements ValorDeEntradaRepository {

	private ValorDeEntradaDao valorDeEntradaDao;

	@Inject
	public ValorDeEntradaRepositoryImpl(ValorDeEntradaDao valorDeEntradaDao) {
		this.valorDeEntradaDao = valorDeEntradaDao;
	}

	/** Apenas para o CDI */
	ValorDeEntradaRepositoryImpl() {
		this(null);
	}

	@Override
	public List<ValorDeEntrada> listaPorTipo(Long idDocumento,TipoValorEntrada tipo) {
		return this.valorDeEntradaDao.listaPorTipo(idDocumento, tipo);
	}

	@Override
	public Boolean valoresDeEntradaForamEncontrados(Long idAluno,
			Long idDocumento) {
		return this.valorDeEntradaDao.valorDeEntradaEncontrado(idAluno, idDocumento);
	}
	
	@Override
	public void atualiza(AlunoEncontraValorDeEntrada valorDeEntrada) {
		this.valorDeEntradaDao.atualiza(valorDeEntrada);
	}
	
	@Override
	public ValorDeEntrada consulta(Long id) {
		return this.valorDeEntradaDao.consulta(id);
	}
	
	@Override
	public List<AlunoEncontraValorDeEntrada> valoresEncontrados(Long idAluno, Long idDocumento) {
		return this.valorDeEntradaDao.valoresEncontrados(idAluno,idDocumento);
	}

	@Override
	public void adiciona(AlunoEncontraValorDeEntrada valorDeEntrada) {
		this.valorDeEntradaDao.adiciona(valorDeEntrada);
	}

}
