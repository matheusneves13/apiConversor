package br.srm.wefin.apiConversor.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ItemVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden = true)
	private Long id;
	
    @ManyToOne 
    @JoinColumn(name = "id_venda", nullable = false) 
    @Schema(hidden = true)
    @JsonIgnore
    private Venda venda;
    
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
    private BigDecimal valorItem;
    
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
    private BigDecimal valorTotal;
    
    @ManyToOne 
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;
    
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal taxaUsada;
    
    private Integer quantidade;
    
	@CreatedDate
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@Schema(hidden = true)
	private LocalDateTime dataCadastro;

	@LastModifiedDate
	@Column(name = "data_atualizacao")
	@Schema(hidden = true)
	private LocalDateTime dataAtualizacao;
	
	public BigDecimal taxaDaVenda() {
		BigDecimal taxa = BigDecimal.ZERO;
		if(produto.getTaxaExclusiva()!=null && produto.getTaxaExclusiva().compareTo(BigDecimal.ZERO)>0) {
			taxa = produto.getTaxaExclusiva();
		}else {
			taxa = produto.getMoeda().getTaxaPadrao();
		}
		return taxa;
	}
	private BigDecimal conversaoDeValor() {
		BigDecimal total = BigDecimal.ZERO;
		if(venda.getMoeda().getTipoMoeda() == produto.getMoeda().getTipoMoeda()) {
			total = produto.getValor();
		}else {
			BigDecimal taxa = taxaDaVenda();
			BigDecimal taxaDestino = BigDecimal.ZERO;

			taxaDestino = venda.getMoeda().getTaxaPadrao();
			BigDecimal taxaConversao = taxaDestino.divide(taxa, 3,RoundingMode.HALF_EVEN);
			
			total = produto.getValor().multiply(taxaConversao);
		}
		return total.setScale(3, RoundingMode.HALF_EVEN);
	}
	public BigDecimal calcularValorUnitario() {
		return conversaoDeValor();
	}
	
	public BigDecimal calcularValorTotal() {
		return getValorItem().multiply(BigDecimal.valueOf(quantidade)).setScale(3, RoundingMode.HALF_EVEN);
	}
}
