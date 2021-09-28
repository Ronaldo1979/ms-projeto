package br.com.cognitivebrasil.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.com.cognitivebrasil.enumerator.Pasta;

public class Secretaria  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Pasta pasta;
	
	private String responsavel;
	
	private Long notaPopulacao;
	
	private Boolean sobInvestigacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Long getNotaPopulacao() {
		return notaPopulacao;
	}

	public void setNotaPopulacao(Long notaPopulacao) {
		this.notaPopulacao = notaPopulacao;
	}

	public Boolean getSobInvestigacao() {
		return sobInvestigacao;
	}

	public void setSobInvestigacao(Boolean sobInvestigacao) {
		this.sobInvestigacao = sobInvestigacao;
	}

	public Secretaria() {
		super();
	}

	public Secretaria(Long id, Pasta pasta, String responsavel, Long notaPopulacao, Boolean sobInvestigacao) {
		super();
		this.id = id;
		this.pasta = pasta;
		this.responsavel = responsavel;
		this.notaPopulacao = notaPopulacao;
		this.sobInvestigacao = sobInvestigacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, notaPopulacao, pasta, responsavel, sobInvestigacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Secretaria other = (Secretaria) obj;
		return Objects.equals(id, other.id) && Objects.equals(notaPopulacao, other.notaPopulacao)
				&& pasta == other.pasta && Objects.equals(responsavel, other.responsavel)
				&& Objects.equals(sobInvestigacao, other.sobInvestigacao);
	}

	@Override
	public String toString() {
		return "Secretaria [id=" + id + ", pasta=" + pasta + ", responsavel=" + responsavel + ", notaPopulacao="
				+ notaPopulacao + ", sobInvestigacao=" + sobInvestigacao + "]";
	}

}
