package br.edu.ifam.underattack.model.repository;

import java.util.List;

import br.edu.ifam.underattack.model.AlunoEncontraValorDeEntrada;
import br.edu.ifam.underattack.model.ValorDeEntrada;
import br.edu.ifam.underattack.model.enums.TipoValorEntrada;


public interface ValorDeEntradaRepository {
	
	List<ValorDeEntrada> listaPorTipo(Long idDocumento, TipoValorEntrada tipo);
	
	Boolean valoresDeEntradaForamEncontrados(Long idAluno, Long idDocumento);
	
	void atualiza(AlunoEncontraValorDeEntrada valorDeEntrada);
	
	void adiciona(AlunoEncontraValorDeEntrada valorDeEntrada);
	
	ValorDeEntrada consulta(Long id);
	
	List<AlunoEncontraValorDeEntrada> valoresEncontrados(Long idAluno, Long idDocumento);

}
