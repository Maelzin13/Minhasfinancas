package com.dsantos.minhasfinancas.service;

import java.util.List;

import com.dsantos.minhasfinancas.model.entity.Lancamento;
import com.dsantos.minhasfinancas.model.enums.StatusLancamento;


public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar ( Lancamento lancamentoFiltro );
	
	void atualizarStatus( Lancamento lancamento, StatusLancamento status);
	
	void validar( Lancamento lancamento );
}
