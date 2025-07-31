package br.srm.wefin.apiConversor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.srm.wefin.apiConversor.dto.VendaRequestCadastro;
import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.model.Venda;
import br.srm.wefin.apiConversor.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/venda")
@Tag(name = "Venda", description = "API para gerenciamento as Vendas")
public class VendaController {
	
	private final VendaService vendaService;

	public VendaController(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PostMapping
	@Operation(summary = "Cria uma venda")
	public ResponseEntity<Venda> criarVenda(@Valid @RequestBody VendaRequestCadastro request){
		Venda venda = vendaService.criarVenda(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(venda);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar uma venda")
	public ResponseEntity<Venda> atualizarVenda(@Parameter(description = "ID da venda") @PathVariable Long id,@Valid @RequestBody VendaRequestCadastro request){
		Venda venda = vendaService.atualizarVenda(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(venda);
	}
	
    @GetMapping("/{id}")
    @Operation(summary = "Seleciona uma venda")
    public ResponseEntity<Venda> buscarVenda(@Parameter(description = "ID da venda") @PathVariable Long id) {
    	Venda response = vendaService.buscarVendaPorIdDTO(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Lista todas as vendas")
    public ResponseEntity<List<Venda>> listarVendas() {
    	List<Venda> response = vendaService.listaVendas();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/lista/{moeda}")
    @Operation(summary = "Lista por moeda")
    public ResponseEntity<List<Venda>> vendasPorMoeda(@Parameter(description = "Moedas:  OURO_REAL, TIBAR.") @PathVariable TipoMoeda moeda) {
    	List<Venda> response = vendaService.findListaPorMoeda(moeda);
        return ResponseEntity.ok(response);
    }
	
	
	
}
