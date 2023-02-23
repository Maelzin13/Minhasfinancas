package com.dsantos.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dsantos.minhasfinancas.model.entity.Usuario;

/*~~(Unable to find runtime dependencies beginning with: 'junit-jupiter-api')~~>*/@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;

    @Test
    void deveVerificarSeExistenciaDeUmEmail() {
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");


        //verificacao
        Assertions.assertThat(result).isTrue();

    }

    @Test
    void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComoOEmail() {
        //cenario
    
                
        //ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void devePersistirUmUsuarioNaBaseDeDados() {
        //cenario
        Usuario usuario	= criarUsuario();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    void deveBuscarUmUsuariosPorEmail() {
        //cenario
        Usuario usuario	= criarUsuario();
        entityManager.persist(usuario);

        //verificacao
         Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    void deveRetornaVazioAoBuscarUsuariosPorEmailQuandoNaoExisteNaBase() {
        //verificacao
         Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }
	
	public static Usuario criarUsuario() {
		return 	Usuario
				.builder()
				.nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
	}
	
}
