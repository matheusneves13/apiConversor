package br.srm.wefin.apiConversor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.srm.wefin.apiConversor.dto.ProdutoRequestCadastro;
import br.srm.wefin.apiConversor.model.Moeda;
import br.srm.wefin.apiConversor.model.Produto;
import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.repository.MoedaRepository;
import br.srm.wefin.apiConversor.repository.ProdutoRepository;
import br.srm.wefin.apiConversor.util.NegocioException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
	

	private final ProdutoRepository produtoRepository;
	private final MoedaRepository moedaRepository;
	
	
	public ProdutoService(ProdutoRepository produtoRepository,MoedaRepository moedaRepository) {
		// TODO Auto-generated constructor stub
		this.produtoRepository = produtoRepository;
		this.moedaRepository = moedaRepository;
	}
	
	
	private Produto buscarPorId(Long id) {
		Produto p = produtoRepository.findById(id).orElseThrow(() -> new NegocioException("Produto não encontrado!"));
		return p;
	}
	
	private Moeda buscarMoedaPorId(Long id) {
		Moeda m = moedaRepository.findById(id).orElseThrow(() -> new NegocioException("Moeda não encontrado!"));
		return m;
	}
	
	private Moeda buscarCotacaoAtualPorTipo(TipoMoeda moeda) {
		Moeda m = moedaRepository.findBuscaByTipoMoeda(moeda).orElseThrow(() -> new NegocioException("Moeda não encontrado!"));
		return m;
	}
	
	private Moeda ultimaCotacao(Long id) {
		Moeda m = buscarCotacaoAtualPorTipo(buscarMoedaPorId(id).getTipoMoeda());
		return m;
	}
	
	@Transactional
	public Produto criarProduto(ProdutoRequestCadastro request) {
		Produto p = new Produto();
		p.setDescricao(request.getDescricao());
		p.setMoeda(ultimaCotacao(request.getMoeda().getId()));
		p.setTaxaExclusiva(request.getTaxaExclusiva());
		p.setValor(request.getValor());
		
		p = produtoRepository.save(p);
		
		return p;
		
	}
	
	
	@Transactional
	public Produto atualizarProduto(Long id,ProdutoRequestCadastro request) {
		Produto p = buscarPorId(id);
		p.setDescricao(request.getDescricao());
		
		p.setMoeda(ultimaCotacao(request.getMoeda().getId()));
		p.setTaxaExclusiva(request.getTaxaExclusiva());
		p.setValor(request.getValor());
		
		p = produtoRepository.save(p);
		
		return p;
		
	}
	
	@Transactional
	public Produto buscarProdutoPorId(Long id) {
		Produto p = buscarPorId(id);
		return p;
	}
	
	@Transactional
	public List<Produto> listaProdutos(){
		List<Produto> list = produtoRepository.findAll().stream().collect(Collectors.toList());
		return list;
	}

	
}
