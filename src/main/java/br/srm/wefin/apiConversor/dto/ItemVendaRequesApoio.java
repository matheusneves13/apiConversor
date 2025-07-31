package br.srm.wefin.apiConversor.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemVendaRequesApoio {
	
	@Schema(hidden = true)
	private Long id;
	
	@NotNull
	private ProdutoRequestApoio produto;
	
	@Digits(integer = 16, fraction = 3)
	@Schema(hidden = true)
	private BigDecimal taxaUsada;
	
	@Positive
	private Integer quantidade;
	
	public ItemVendaRequesApoio() {
		// TODO Auto-generated constructor stub
	}

	public ItemVendaRequesApoio(Long id,@NotNull ProdutoRequestApoio produto, @Positive Integer quantidade,BigDecimal taxaUsada) {
		super();
		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
		this.taxaUsada = taxaUsada;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public ProdutoRequestApoio getProduto() {
		return produto;
	}

	public void setProduto(ProdutoRequestApoio produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getTaxaUsada() {
		return taxaUsada;
	}
	
	public void setTaxaUsada(BigDecimal taxaUsada) {
		this.taxaUsada = taxaUsada;
	}
	
}
