package com.dsantos.minhasfinancas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dsantos.minhasfinancas.exception.RegraNegocioExcepition;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.repository.UsuarioRepository;
import com.dsantos.minhasfinancas.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
public class UsuarioServiceTest {
	

	UsuarioService service;
	
	@MockBean 
	UsuarioRepository repository;
	
	@Before
	public void setUp() {		
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioExcepition.class)
	public void  deveLancarErroAoValidarQuandoExistirEmailCadastrado() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		service.validarEmail("email@email.com");
	}
}
