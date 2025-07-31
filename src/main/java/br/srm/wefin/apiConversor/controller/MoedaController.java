package br.srm.wefin.apiConversor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.srm.wefin.apiConversor.dto.MoedaRequestCadastro;
import br.srm.wefin.apiConversor.model.Moeda;
import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.service.MoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/moeda")
@Tag(name = "Moeda", description = "Api para gerenciamento das moedas")
public class MoedaController {
	
	private final MoedaService moedaService;
	
	public MoedaController(MoedaService moedaService) {
		this.moedaService = moedaService;
	}
	
	@PostMapping
	@Operation(summary = "Cria uma moeda nova, funciona também para atualizar a cotação!")
	public ResponseEntity<Moeda> criarMoeda(@Valid @RequestBody MoedaRequestCadastro request){
		Moeda moeda = moedaService.criarMoeda(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(moeda);
	}
	
    @GetMapping("/{moeda}")
    @Operation(summary = "Seleciona uma moeda e informa a cotação atual")
    public ResponseEntity<Moeda> buscaMoeda(@Parameter(description = "Moedas:  OURO_REAL, TIBAR.")@PathVariable TipoMoeda moeda) {
    
    	Moeda response = moedaService.buscarCotacaoAtual(moeda);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/historico/{moeda}")
    @Operation(summary = "Lista o historico de cotação da moeda")
    public ResponseEntity<List<Moeda>> buscaHistorico(@Parameter(description = "Moedas:  OURO_REAL, TIBAR.")@PathVariable TipoMoeda moeda) {
    	
        List<Moeda> response = moedaService.getListaHistoricoMoeda(moeda);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Lista todas as moedas e sua cotação")
    public ResponseEntity<List<Moeda>> buscaHistorico() {
        List<Moeda> response = moedaService.getListaCotacao();
        return ResponseEntity.ok(response);
    }
}
