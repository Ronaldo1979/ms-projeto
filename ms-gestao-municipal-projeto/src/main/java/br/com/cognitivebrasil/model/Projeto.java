package br.com.cognitivebrasil.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cognitivebrasil.enumerator.Pasta;

@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "secretaria_id")
	private Long secretariaId;
	
	@Column(name = "custo_projeto")
	private Double custoProjeto;
	
	@Column(name = "titulo")
	private String Titulo;
	
	@Column(name = "descricao_projeto")
	private String descricaoProjeto;
	
	@Enumerated(EnumType.STRING)
	private Pasta pasta;

	public Projeto() {
		super();
	}

	public Projeto(Long id, Long secretariaId, Double custoProjeto, String titulo, String descricaoProjeto,
			Pasta pasta) {
		super();
		this.id = id;
		this.secretariaId = secretariaId;
		this.custoProjeto = custoProjeto;
		Titulo = titulo;
		this.descricaoProjeto = descricaoProjeto;
		this.pasta = pasta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSecretariaId() {
		return secretariaId;
	}

	public void setSecretariaId(Long secretariaId) {
		this.secretariaId = secretariaId;
	}

	public Double getCustoProjeto() {
		return custoProjeto;
	}

	public void setCustoProjeto(Double custoProjeto) {
		this.custoProjeto = custoProjeto;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public String getDescricaoProjeto() {
		return descricaoProjeto;
	}

	public void setDescricaoProjeto(String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Titulo, custoProjeto, descricaoProjeto, id, pasta, secretariaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		return Objects.equals(Titulo, other.Titulo) && Objects.equals(custoProjeto, other.custoProjeto)
				&& Objects.equals(descricaoProjeto, other.descricaoProjeto) && Objects.equals(id, other.id)
				&& pasta == other.pasta && Objects.equals(secretariaId, other.secretariaId);
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", secretariaId=" + secretariaId + ", custoProjeto=" + custoProjeto + ", Titulo="
				+ Titulo + ", descricaoProjeto=" + descricaoProjeto + ", pasta=" + pasta + "]";
	}

	

}
