package br.srm.wefin.apiConversor.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal valor;
	
	@Digits(integer = 16, fraction = 3)
	@Column(precision = 19, scale = 3)
	private BigDecimal taxaExclusiva;
	
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

}
