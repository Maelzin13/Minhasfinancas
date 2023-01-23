package com.dsantos.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dsantos.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarSeExistenciaDeUmEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("name").email("usuario@email.com").build();
		repository.save(usuario);
		
		//ação / execução
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		//verificacao
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComoOEmail() {
		//cenario
		repository.deleteAll();
				
		//ação / execução
		boolean result = repository.existsByEmail("usuario@email.com");
				
		//verificacao
		Assertions.assertThat(result).isFalse();	
	}
	
}
