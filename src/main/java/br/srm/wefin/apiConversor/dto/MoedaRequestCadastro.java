package br.srm.wefin.apiConversor.dto;

import java.math.BigDecimal;

import br.srm.wefin.apiConversor.model.TipoMoeda;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class MoedaRequestCadastro {
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal taxaPadrao;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private TipoMoeda tipoMoeda;

	public MoedaRequestCadastro() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MoedaRequestCadastro(@Digits(integer = 16, fraction = 3) BigDecimal taxaPadrao,
			@NotNull TipoMoeda tipoMoeda) {
		super();
		this.taxaPadrao = taxaPadrao;
		this.tipoMoeda = tipoMoeda;
	}


	public BigDecimal getTaxaPadrao() {
		return taxaPadrao;
	}

	public void setTaxaPadrao(BigDecimal taxaPadrao) {
		this.taxaPadrao = taxaPadrao;
	}

	public TipoMoeda getTipoMoeda() {
		return tipoMoeda;
	}

	public void setTipoMoeda(TipoMoeda tipoMoeda) {
		this.tipoMoeda = tipoMoeda;
	}
	
	
}
