package br.srm.wefin.apiConversor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.srm.wefin.apiConversor.dto.ItemVendaRequesApoio;
import br.srm.wefin.apiConversor.dto.VendaRequestCadastro;
import br.srm.wefin.apiConversor.model.ItemVenda;
import br.srm.wefin.apiConversor.model.Moeda;
import br.srm.wefin.apiConversor.model.Produto;
import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.model.Venda;
import br.srm.wefin.apiConversor.repository.ItemVendaRepository;
import br.srm.wefin.apiConversor.repository.MoedaRepository;
import br.srm.wefin.apiConversor.repository.ProdutoRepository;
import br.srm.wefin.apiConversor.repository.VendaRepository;
import br.srm.wefin.apiConversor.util.NegocioException;
import jakarta.transaction.Transactional;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	private final ProdutoRepository produtoRepository;
	private final MoedaRepository moedaRepository;
	private final ItemVendaRepository itemVendaRepository;

	public VendaService(VendaRepository vendaRepository,ItemVendaRepository itemVendaRepository,ProdutoRepository produtoRepository,MoedaRepository moedaRepository) {
		this.vendaRepository = vendaRepository;
		this.itemVendaRepository = itemVendaRepository;
		this.produtoRepository = produtoRepository;
		this.moedaRepository = moedaRepository;
	}

	
	private Produto buscarProdutoPorId(Long id) {
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
	
	private Venda buscaVendaPorId(Long id) {
		  Venda venda = vendaRepository.findById(id)
	                .orElseThrow(() -> new NegocioException("Venda não encontrada!"));
		  return venda;
	}
	
	@Transactional
	public Venda criarVenda(VendaRequestCadastro request) {
		Venda venda = new Venda();
			
		venda.setValorVenda(BigDecimal.ZERO);
		
		Moeda m = ultimaCotacao(request.getMoeda().getId());
		venda.setMoeda(m);
		List<ItemVenda> list = new ArrayList<ItemVenda>();
		for(ItemVendaRequesApoio itemDTO:request.getItens()) {
			Produto p = buscarProdutoPorId(itemDTO.getProduto().getId());
			ItemVenda item = new ItemVenda();
			item.setProduto(p);
			item.setTaxaUsada(item.taxaDaVenda());
			item.setQuantidade(itemDTO.getQuantidade());
			item.setVenda(venda);
			item.setValorItem(item.calcularValorUnitario());
			item.setValorTotal(item.calcularValorTotal());
			
			list.add(item);
		}
		venda.setItens(list);
		venda.setValorVenda(venda.calcularValorTotal());
		Venda savedVenda = vendaRepository.save(venda);
		return savedVenda;
	}
	
	@Transactional
	public Venda atualizarVenda(Long id, VendaRequestCadastro requestDTO) {
		Venda venda = buscaVendaPorId(id);
		Moeda moeda = ultimaCotacao(requestDTO.getMoeda().getId());
		venda.setMoeda(moeda);
		Set<Long> requestItemIds = requestDTO.getItens().stream()
                .map(ItemVendaRequesApoio::getId)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
		venda.getItens().removeIf(itemExistente ->
        itemExistente.getId() != null && !requestItemIds.contains(itemExistente.getId())
        		);
		venda.setValorVenda(BigDecimal.ZERO);
		for(ItemVendaRequesApoio itemDTO:requestDTO.getItens()) {
			if (itemDTO.getId() == null) {
				Produto p = buscarProdutoPorId(itemDTO.getProduto().getId());
				ItemVenda item = new ItemVenda();
				item.setProduto(p);
				item.setQuantidade(itemDTO.getQuantidade());
				item.setVenda(venda);
				item.setTaxaUsada(item.taxaDaVenda());
				item.setValorItem(item.calcularValorUnitario());
				item.setValorTotal(item.calcularValorTotal());
				venda.getItens().add(item);
			}else {
				venda.getItens().stream()
                .filter(itemExistente -> itemExistente.getId().equals(itemDTO.getId()))
                .findFirst()
                .ifPresent(itemExistente -> {
                    // Atualiza campos do item existente com base em requestItem
                    itemExistente.setQuantidade(itemDTO.getQuantidade());
                    Produto p = buscarProdutoPorId(itemDTO.getProduto().getId()); //
                    itemExistente.setProduto(p);
                    itemExistente.setTaxaUsada(itemExistente.taxaDaVenda());
                    itemExistente.setValorItem(itemExistente.calcularValorUnitario()); //
                    itemExistente.setValorTotal(itemExistente.calcularValorTotal()); //
                });
			}

		}
		
		venda.setValorVenda(venda.calcularValorTotal());
		
		Venda savedVenda = vendaRepository.save(venda);
		return savedVenda;
		
	}
	
	@Transactional
	public Venda buscarVendaPorIdDTO(Long id) {
		return buscaVendaPorId(id);
	}
	
	@Transactional
	public List<Venda> listaVendas(){
		List<Venda> list = vendaRepository.findAll().stream().collect(Collectors.toList());
		return list;
	}
	
	@Transactional
	public List<Venda> findListaPorMoeda(TipoMoeda moeda){
		
		List<Venda> list = vendaRepository.findListaPorMoeda(moeda).stream().collect(Collectors.toList());
		return list;
	}
	

	
	
}
