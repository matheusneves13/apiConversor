package br.srm.wefin.apiConversor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.srm.wefin.apiConversor.model.Moeda;
import br.srm.wefin.apiConversor.model.TipoMoeda;

public interface MoedaRepository extends JpaRepository<Moeda,Long>{
	
	@Query("SELECT cm FROM Moeda cm WHERE cm.tipoMoeda = :tipoMoeda ORDER BY cm.dataCadastro DESC LIMIT 1")
    Optional<Moeda> findBuscaByTipoMoeda(@Param("tipoMoeda") TipoMoeda moeda);
	
	List<Moeda> findByTipoMoedaOrderByDataCadastroDesc(TipoMoeda tipoMoeda);
	
	@Query("SELECT cm FROM Moeda cm WHERE cm.dataCadastro = (SELECT MAX(cm2.dataCadastro) FROM Moeda cm2 WHERE cm2.tipoMoeda = cm.tipoMoeda )")
	List<Moeda> findMoedasAll();
}	
