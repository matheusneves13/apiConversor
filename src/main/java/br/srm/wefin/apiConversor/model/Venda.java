package br.srm.wefin.apiConversor.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVenda> itens;
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal valorVenda;
	
	@ManyToOne
	@JoinColumn(name = "id_moeda", nullable = false)
	private Moeda moeda;
	
	@CreatedDate
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@Schema(hidden = true)
	private LocalDateTime dataCadastro;

	@LastModifiedDate
	@Column(name = "data_atualizacao")
	@Schema(hidden = true)
	private LocalDateTime dataAtualizacao;
	
	public BigDecimal calcularValorTotal() {		
		valorVenda = BigDecimal.ZERO;
		for(ItemVenda e:itens){	
			valorVenda = valorVenda.add(e.calcularValorTotal());
		}
		return valorVenda.setScale(3, RoundingMode.HALF_EVEN);
	}
}
