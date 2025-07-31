package br.srm.wefin.apiConversor.dto;

import java.math.BigDecimal;

import br.srm.wefin.apiConversor.model.Moeda;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoRequestCadastro {
	
	@NotBlank
	private String descricao;
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal valor;
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal taxaExclusiva;
	
	@NotNull
	private MoedaRequestApoio moeda;
	
	public ProdutoRequestCadastro() {
		// TODO Auto-generated constructor stub
	}

	public ProdutoRequestCadastro(@NotBlank String descricao, @Digits(integer = 16, fraction = 3) BigDecimal valor,
		@Digits(integer = 16, fraction = 3) BigDecimal taxaExclusiva, @NotNull MoedaRequestApoio moeda) {
	super();
	this.descricao = descricao;
	this.valor = valor;
	this.taxaExclusiva = taxaExclusiva;
	this.moeda = moeda;
}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTaxaExclusiva() {
		return taxaExclusiva;
	}

	public void setTaxaExclusiva(BigDecimal taxaExclusiva) {
		this.taxaExclusiva = taxaExclusiva;
	}

	public MoedaRequestApoio getMoeda() {
		return moeda;
	}

	public void setMoeda(MoedaRequestApoio moeda) {
		this.moeda = moeda;
	}
	
	
}
