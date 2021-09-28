package br.com.cognitivebrasil.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.cognitivebrasil.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByEmail(String email);
}
