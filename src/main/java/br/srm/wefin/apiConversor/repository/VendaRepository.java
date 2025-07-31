package br.srm.wefin.apiConversor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.srm.wefin.apiConversor.model.TipoMoeda;
import br.srm.wefin.apiConversor.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
	
	@Query("SELECT v FROM Venda v JOIN v.moeda m WHERE m.tipoMoeda = :tipoMoeda ORDER BY m.tipoMoeda DESC")
	List<Venda> findListaPorMoeda(@Param("tipoMoeda")TipoMoeda moeda);
}
