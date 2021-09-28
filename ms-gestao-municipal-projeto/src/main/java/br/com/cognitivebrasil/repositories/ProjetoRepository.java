package br.com.cognitivebrasil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cognitivebrasil.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

}
