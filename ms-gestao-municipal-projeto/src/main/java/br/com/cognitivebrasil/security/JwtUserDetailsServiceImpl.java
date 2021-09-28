package br.com.cognitivebrasil.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.cognitivebrasil.model.Usuario;
import br.com.cognitivebrasil.repositories.UsuarioRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByEmail(username));
			if (usuario.isPresent()) {
				return JwtUserFactory.create(usuario.get());
			}
			//throw new UsernameNotFoundException("Email não encontrado.");
			return null;
	}

}
