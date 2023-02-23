package com.dsantos.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsantos.minhasfinancas.api.dto.LancamentoDTO;
import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Lancamento;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.enums.StatusLancamento;
import com.dsantos.minhasfinancas.model.enums.TipoLancamento;
import com.dsantos.minhasfinancas.service.LancamentoService;
import com.dsantos.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

	private LancamentoService service;
	private UsuarioService usuarioService;
	
	public LancamentoResource( LancamentoService service) {
		this.service = service; 
	}
	
	@PostMapping
	public ResponseEntity salver( @RequestBody  LancamentoDTO dto ) {
		try {
			Lancamento entidade = converter(dto);		
			entidade = service.salvar(entidade);		
			return new ResponseEntity<> (entidade, HttpStatus.CREATED);
		}catch (RegraNegocioExcepition e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody LancamentoDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);				
			}catch (RegraNegocioExcepition e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}).orElseGet( () -> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ){
		return service.obterPorId(id).map( entidade -> {
				service.deletar(entidade);
				return new ResponseEntity( HttpStatus.NO_CONTENT);
		}).orElseGet( () -> new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	private Lancamento converter( LancamentoDTO dto ) {
		Lancamento lancamento = new Lancamento(); 
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		
		Usuario usuario = usuarioService.obterPorId(dto.getUsuario())
		.orElseThrow( () -> new RegraNegocioExcepition("Usuario não encotnrado para o Id informado.") );
		
		lancamento.setUsuario(usuario);
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		
		return lancamento;
	}
	
}