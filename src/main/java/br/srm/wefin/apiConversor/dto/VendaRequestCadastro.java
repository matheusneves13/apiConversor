package br.srm.wefin.apiConversor.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class VendaRequestCadastro {
	
	@NotNull
	private List<ItemVendaRequesApoio> itens;
	
	@NotNull
	private MoedaRequestApoio moeda;
	
	public VendaRequestCadastro() {
		// TODO Auto-generated constructor stub
	}
	
	

	public VendaRequestCadastro(@NotNull List<ItemVendaRequesApoio> itens, @NotNull MoedaRequestApoio moeda) {
		super();
		this.itens = itens;
		this.moeda = moeda;
	}



	public List<ItemVendaRequesApoio> getItens() {
		return itens;
	}

	public void setItens(List<ItemVendaRequesApoio> itens) {
		this.itens = itens;
	}

	public MoedaRequestApoio getMoeda() {
		return moeda;
	}

	public void setMoeda(MoedaRequestApoio moeda) {
		this.moeda = moeda;
	}
	
	
}
