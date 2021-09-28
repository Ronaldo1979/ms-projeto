package br.com.cognitivebrasil.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cognitivebrasil.enumerator.PerfilUser;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "perfil")
	private PerfilUser perfil;

	public Usuario() {
		super();
	}

	public Usuario(String email, String senha, PerfilUser perfil) {
		super();
		this.email = email;
		this.senha = senha;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilUser getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUser perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, perfil, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && perfil == other.perfil
				&& Objects.equals(senha, other.senha);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", senha=" + senha + ", perfil=" + perfil + "]";
	}
	
}

	