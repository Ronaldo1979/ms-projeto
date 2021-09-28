package br.com.cognitivebrasil.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.cognitivebrasil.enumerator.PerfilUser;
import br.com.cognitivebrasil.feignclients.OrcamentoFeignClient;
import br.com.cognitivebrasil.feignclients.SecretariaFeignClient;
import br.com.cognitivebrasil.model.Orcamento;
import br.com.cognitivebrasil.model.Projeto;
import br.com.cognitivebrasil.model.Secretaria;
import br.com.cognitivebrasil.model.Usuario;
import br.com.cognitivebrasil.repositories.ProjetoRepository;
import br.com.cognitivebrasil.repositories.UsuarioRepository;
import br.com.cognitivebrasil.util.Response;
import br.com.cognitivebrasil.util.TokenActive;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SecretariaFeignClient secretariaFeignClient;
	
	@Autowired
	private OrcamentoFeignClient orcamentoFeignClient;
	
	public Secretaria buscaSecretaria(Long secretariaId) {
		
		ResponseEntity<Response<Secretaria>> secretaria = null;
		
		try {
			
			secretaria = secretariaFeignClient.buscaSecretaria(TokenActive.TOKEN, secretariaId);
			if(secretaria != null)
				return secretaria.getBody().getData();
			else
				return null;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Orcamento buscaOrcamento(String pasta) {
		
		ResponseEntity<Response<Orcamento>> orcamento = null;
		
		try {

			orcamento = orcamentoFeignClient.buscaOrcamentoPorDestino(TokenActive.TOKEN, pasta);
			if(orcamento != null)
				return orcamento.getBody().getData();
			else
				return null;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Projeto cadastraProjeto(Projeto obj) {
		Projeto projeto = null;
		try {
			projeto = repository.save(obj);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			repository.flush();
		}
		return projeto;
	}
	
	public Optional<Projeto> buscaProjeto(Long objId) {
		Optional<Projeto> projeto = null;
		try {
			projeto = repository.findById(objId);
		}catch (NoSuchElementException e) {
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			repository.flush();
		}
		return projeto;
	}
	
	public List<Projeto> listaProjeto() {
		List<Projeto> listaProjeto = null;
		try {
			listaProjeto = repository.findAll();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			repository.flush();
		}
		return listaProjeto;
	}
	
	public Projeto atualizaProjeto(Projeto obj) {
		Projeto projeto = null;
		try {
			projeto = repository.saveAndFlush(obj);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			repository.flush();
		}
		return projeto;
	}
	
	//USER==================================================================//
	
	public Usuario cadastraUsuario(Usuario obj) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.save(obj);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			usuarioRepository.flush();
		}
		return usuario;
	}
	
	public Usuario atualizaUsuario(Usuario obj) {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.saveAndFlush(obj);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			usuarioRepository.flush();
		}
		return usuario;
	}
	
	public Optional<Usuario> buscaUsuario(String email) {
		
		Optional<Usuario> usuario = null;
		
		try {
			usuario = Optional.ofNullable(usuarioRepository.findByEmail(email));
		}catch (NoSuchElementException e) {
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			usuarioRepository.flush();
		}
		return usuario;
	}
	
	public List<Usuario> listaUsuario() {
		List<Usuario> listaUsuario = null;
		try {
			listaUsuario = usuarioRepository.findAll();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			usuarioRepository.flush();
		}
		return listaUsuario;
	}
	
	public void criaUsuarioPadrao() {
		try {
			Usuario usuario = usuarioRepository.findByEmail("ronaldo.fjv@gmail.com");
			if(usuario == null) {
				usuario = new Usuario();
				usuario.setEmail("ronaldo.fjv@gmail.com");
				usuario.setPerfil(PerfilUser.ROLE_ADMIN);
				usuario.setSenha("forcajovemvasco");
				
				Gson gson = new Gson();
				System.out.println(gson.toJson(usuario));
				
				usuarioRepository.save(usuario);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
