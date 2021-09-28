package br.com.cognitivebrasil.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.com.cognitivebrasil.enumerator.Origem;
import br.com.cognitivebrasil.enumerator.Pasta;

public class Orcamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Double totalOrcamento;
	private Double valorGasto;
	
	@Enumerated(EnumType.STRING)
	private Pasta destino;
	
	@Enumerated(EnumType.STRING)
	private Origem origem;

	public Orcamento() {
		super();
	}

	public Orcamento(Long id, Double totalOrcamento, Double valorGasto, Pasta destino, Origem origem) {
		super();
		this.id = id;
		this.totalOrcamento = totalOrcamento;
		this.valorGasto = valorGasto;
		this.destino = destino;
		this.origem = origem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalOrcamento() {
		return totalOrcamento;
	}

	public void setTotalOrcamento(Double totalOrcamento) {
		this.totalOrcamento = totalOrcamento;
	}

	public Double getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(Double valorGasto) {
		this.valorGasto = valorGasto;
	}

	public Pasta getDestino() {
		return destino;
	}

	public void setDestino(Pasta destino) {
		this.destino = destino;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(destino, id, origem, totalOrcamento, valorGasto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamento other = (Orcamento) obj;
		return destino == other.destino && Objects.equals(id, other.id) && origem == other.origem
				&& Objects.equals(totalOrcamento, other.totalOrcamento) && Objects.equals(valorGasto, other.valorGasto);
	}

	@Override
	public String toString() {
		return "Orcamento [id=" + id + ", totalOrcamento=" + totalOrcamento + ", valorGasto=" + valorGasto + ", destino="
				+ destino + ", origem=" + origem + "]";
	}

}
