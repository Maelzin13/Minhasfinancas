package com.dsantos.minhasfinancas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.repository.UsuarioRepository;
import com.dsantos.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		boolean exite = repository.existsByEmail(email);
		if(exite) {
			throw new RegraNegocioExcepition("Ja existe um usuário cafastrado com este email.");
		}
		
	}

}
