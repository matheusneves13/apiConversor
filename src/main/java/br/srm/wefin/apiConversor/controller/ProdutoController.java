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

import br.srm.wefin.apiConversor.dto.ProdutoRequestCadastro;
import br.srm.wefin.apiConversor.model.Produto;
import br.srm.wefin.apiConversor.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "Api para gerenciamento o cadastro de produtos")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		// TODO Auto-generated constructor stub
		this.produtoService = produtoService;
	}
	
	@PostMapping
	@Operation(summary = "Cria um produto")
	public ResponseEntity<Produto> criarProduto(@Valid @RequestBody ProdutoRequestCadastro request){
		Produto r = produtoService.criarProduto(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(r);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar um produto")
	public ResponseEntity<Produto> atualizarProduto(@Parameter(description = "ID do produto")@PathVariable Long id,@Valid @RequestBody ProdutoRequestCadastro request){
		Produto r = produtoService.atualizarProduto(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(r);
	}
	
    @GetMapping("/{id}")
    @Operation(summary = "Seleciona um Produto")
    public ResponseEntity<Produto> buscaProduto(@Parameter(description = "ID do produto")@PathVariable Long id) {
    	Produto response = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Lista de Produtos")
    public ResponseEntity<List<Produto>> listaProdutos() {
    	List<Produto> response = produtoService.listaProdutos();
        return ResponseEntity.ok(response);
    }
}
