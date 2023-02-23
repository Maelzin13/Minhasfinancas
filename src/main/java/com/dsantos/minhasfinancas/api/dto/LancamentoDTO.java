package com.dsantos.minhasfinancas.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LancamentoDTO {

	private Long id;
	private String descricao;
	private Integer mes;
	private Integer ano;
	private BigDecimal valor;
	private Long usuario;
	private String tipo;
	private String status;
	
}
