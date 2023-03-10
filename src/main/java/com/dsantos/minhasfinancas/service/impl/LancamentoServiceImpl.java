package com.dsantos.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Lancamento;
import com.dsantos.minhasfinancas.model.enums.StatusLancamento;
import com.dsantos.minhasfinancas.model.repository.LancamentoRepository;
import com.dsantos.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	private LancamentoRepository repository;
	
	public LancamentoServiceImpl( LancamentoRepository repository ) {
		 this.repository = repository;
	}
	
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {		
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validar(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example exemple = Example.of( lancamentoFiltro, 
				ExampleMatcher.matching().
				withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(exemple);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}


	@Override
	public void validar(Lancamento lancamento) {
		
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("") ) {
			throw new RegraNegocioExcepition("Informe uma Descri????o v??lida.");
		}
			
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioExcepition("Informe uma M??s v??lido.");	
		}	
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioExcepition("Informe uma Ano v??lido.");	
		}	
		
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null ) {
			throw new RegraNegocioExcepition("Informe um Usu??rio.");	
		}
		
		if(lancamento.getValor() == null ||  lancamento.getValor().compareTo(BigDecimal.ZERO) < 1 ) {
			throw new RegraNegocioExcepition("Informe um Valor v??lido.");	
		}
		
		if(lancamento.getTipo() == null ) {
			throw new RegraNegocioExcepition("Informe um tipo de Lan??amento.");	
		}
		
	}

}
