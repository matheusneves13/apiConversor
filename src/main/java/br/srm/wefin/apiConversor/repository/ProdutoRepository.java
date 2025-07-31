package br.srm.wefin.apiConversor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.srm.wefin.apiConversor.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
