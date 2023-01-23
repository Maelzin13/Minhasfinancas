package com.dsantos.minhasfinancas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenario
		repository.deleteAll();
		
		//acao
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioExcepition.class)
	public void  deveLancarErroAoValidarQuandoExistirEmailCadastrado() {
		// cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
		repository.save(usuario);
		
		//acao
		service.validarEmail("email@email.com");
	}
}
