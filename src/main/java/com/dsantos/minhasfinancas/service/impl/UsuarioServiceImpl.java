package com.dsantos.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsantos.minhasfinancas.exception.ErroAutenticacao;
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
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new  ErroAutenticacao("Usuário não encontrado para o email informado."); 
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new  ErroAutenticacao("Senha inválida."); 
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean exite = repository.existsByEmail(email);
		if(exite) {
			throw new RegraNegocioExcepition("Ja existe um usuário cafastrado com este email.");
		}
		
	}

}
