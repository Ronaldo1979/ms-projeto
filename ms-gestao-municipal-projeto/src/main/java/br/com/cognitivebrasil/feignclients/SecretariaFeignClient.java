package br.com.cognitivebrasil.feignclients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import br.com.cognitivebrasil.model.Secretaria;
import br.com.cognitivebrasil.util.Response;

@Service
@FeignClient(name = "ms-secretaria", url="localhost:8085", path = "/resource")
public interface SecretariaFeignClient {

	@GetMapping(value = "secretariats/{secretariaId}")
	ResponseEntity<Response<Secretaria>> buscaSecretaria(@RequestHeader("Authorization") String bearerToken, @PathVariable("secretariaId") Long secretariaId);
}
