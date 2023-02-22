package com.dsantos.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsantos.minhasfinancas.api.dto.UsuarioDTO;
import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	private UsuarioService service;

	public UsuarioResource( UsuarioService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto) {
<<<<<<< HEAD:src/main/java/com/dsantos/minhasfinancas/api/resouce/UsuarioResource.java
		
		Usuario usuario =  Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
		
=======

		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

>>>>>>> 5d447603ba46d394604e5d9842c15c112a31a06d:src/main/java/com/dsantos/minhasfinancas/api/resource/UsuarioResource.java
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioExcepition e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}