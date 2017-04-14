package br.edu.ifam.underattack.dao;

import java.util.List;

import br.edu.ifam.underattack.model.AlunoEncontraValorDeEntrada;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.TipoValorEntrada;

public interface ValorDeEntradaDao {

	List<ValorDeEntrada> listaPorTipo(Long idDocumento, TipoValorEntrada tipo);

	Boolean valorDeEntradaEncontrado(Long idAluno,
                                     Long idDocumento);

	void atualiza(AlunoEncontraValorDeEntrada valorDeEntrada);

	ValorDeEntrada consulta(Long id);

	List<AlunoEncontraValorDeEntrada> valoresEncontrados(Long idAluno, Long idDocumento);

	void adiciona(AlunoEncontraValorDeEntrada valorDeEntrada);
	

}
