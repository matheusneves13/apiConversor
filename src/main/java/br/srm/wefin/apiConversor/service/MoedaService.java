package br.srm.wefin.apiConversor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.srm.wefin.apiConversor.dto.MoedaRequestCadastro;
import br.srm.wefin.apiConversor.model.Moeda;
import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.repository.MoedaRepository;
import br.srm.wefin.apiConversor.util.NegocioException;
import jakarta.transaction.Transactional;


@Service
public class MoedaService {
	
	
	private final MoedaRepository moedaRepository;
	
	
	
	public MoedaService(MoedaRepository moedaRepository) {
		this.moedaRepository = moedaRepository;
	}
	

	
	@Transactional
	public Moeda criarMoeda(MoedaRequestCadastro request) {
		Moeda moeda = new Moeda();
		moeda.setTipoMoeda(request.getTipoMoeda());
		if(TipoMoeda.OURO_REAL == request.getTipoMoeda()) {
			if(request.getTaxaPadrao()!=BigDecimal.ONE) {
				throw new NegocioException("Essa moeda é a base, sempre será um");
			}
			moeda.setTaxaPadrao(BigDecimal.ONE);
		}else {
			moeda.setTaxaPadrao(request.getTaxaPadrao());
		}
		moeda = moedaRepository.save(moeda);
		return moeda;
		
	}
	
	@Transactional
	public Moeda buscarCotacaoAtual(TipoMoeda tipoMoeda) {
		Moeda moeda = moedaRepository.findBuscaByTipoMoeda(tipoMoeda)
                .orElseThrow(() -> new NegocioException("Nenhuma cotação encontrada para a moeda: " + tipoMoeda));
		return moeda;
	}
	
	@Transactional
	public List<Moeda> getListaHistoricoMoeda(TipoMoeda moeda){
		if(moeda==null) {
			throw new NegocioException("O tipo de moeda não pode ser nulo para buscar o histórico.");
		}
		List<Moeda> list = moedaRepository.findByTipoMoedaOrderByDataCadastroDesc(moeda).stream().collect(Collectors.toList());
		return list;
	}
	
	@Transactional
	public List<Moeda> getListaCotacao(){
		return moedaRepository.findMoedasAll().stream().collect(Collectors.toList());
	}

	
	
}
