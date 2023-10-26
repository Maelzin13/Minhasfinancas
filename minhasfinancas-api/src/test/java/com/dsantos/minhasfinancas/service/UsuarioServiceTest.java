package com.dsantos.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dsantos.minhasfinancas.exception.ErroAutenticacao;
import com.dsantos.minhasfinancas.exception.RegraNegocioException;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.repository.UsuarioRepository;
import com.dsantos.minhasfinancas.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;

    @Test
    void deveSalvarUmUsuario() {
		//cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder()
					.id(1l)
					.nome("nome")
					.email("email@email.com")
					.senha("senha").build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		//verificao
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}

    @Test
    void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		//cenario
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//acao
		assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario) ) ;
		
		//verificacao
		Mockito.verify( repository, Mockito.never() ).save(usuario);
	}

    @Test
    void deveAutenticarUmUsuarioComSucesso() {
		//cenário
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when( repository.findByEmail(email) ).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = service.autenticar(email, senha);
		
		//verificacao
		Assertions.assertThat(result).isNotNull();
		
	}

    @Test
    void deveLancarErroQUandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		//cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha") );
		
		//verificacao
		Assertions.assertThat(exception)
			.isInstanceOf(ErroAutenticacao.class)
			.hasMessage("Usuário não encontrado para o email informado.");
	}

    @Test
    void deveLancarErroQuandoSenhaNaoBater() {
		//cenario
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//acao
		Throwable exception = Assertions.catchThrowable( () ->  service.autenticar("email@email.com", "123") );
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
		
	}

    @Test
    void deveValidarEmail() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validarEmail("email@email.com");
	}

    @Test
    void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@email.com"));
	}
}