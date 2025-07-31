package br.srm.wefin.apiConversor.dto;

import jakarta.validation.constraints.Positive;

public class ProdutoRequestApoio {
	
	@Positive
	private Long id;
	
	public ProdutoRequestApoio() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ProdutoRequestApoio(@Positive Long id) {
		super();
		this.id = id;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
