package br.srm.wefin.apiConversor.dto;

import jakarta.validation.constraints.NotNull;

public class MoedaRequestApoio {

	@NotNull
	private Long id;
	
	public MoedaRequestApoio() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MoedaRequestApoio(@NotNull Long id) {
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
