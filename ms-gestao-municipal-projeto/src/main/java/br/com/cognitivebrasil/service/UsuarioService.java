package br.com.cognitivebrasil.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.cognitivebrasil.model.Usuario;
import br.com.cognitivebrasil.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
	public Usuario buscaUsuario(String email) {
		
		Usuario usuario = null;
		
		try {
			usuario = usuarioRepository.findByEmail(email);
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
}
