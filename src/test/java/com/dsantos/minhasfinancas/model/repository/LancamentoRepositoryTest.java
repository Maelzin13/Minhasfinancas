package com.dsantos.minhasfinancas.model.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

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

import com.dsantos.minhasfinancas.model.entity.Lancamento;
import com.dsantos.minhasfinancas.model.entity.Usuario;
import com.dsantos.minhasfinancas.model.enums.StatusLancamento;
import com.dsantos.minhasfinancas.model.enums.TipoLancamento;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarUmLancamento() {
	    Usuario usuario = Usuario.builder()
	            .nome("usuario")
	            .email("usuario@email.com")
	            .senha("senha")
	            .build();
	    usuario = entityManager.persist(usuario);
	    
	    Lancamento lancamento = criarLancamento(usuario);
	    
	    lancamento = repository.save(lancamento);
	    
	    Assertions.assertThat(lancamento.getId()).isNotNull();

	}


	private Lancamento criarLancamento(Usuario usuario) {
		return Lancamento.builder()
				.ano(2023)
				.mes(1)
				.descricao("lancamento qualquer")
				.valor(BigDecimal.valueOf(10))
				.tipo(TipoLancamento.RECEITA)
				.status(StatusLancamento.CANCELADO)
				.dataCadastro(LocalDate.now())
				.usuario(usuario) // Associa o usuário ao lançamento
				.build();
	}
}
	

