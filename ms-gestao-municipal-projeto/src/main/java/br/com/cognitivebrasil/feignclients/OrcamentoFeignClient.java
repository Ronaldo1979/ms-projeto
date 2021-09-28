package br.com.cognitivebrasil.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import br.com.cognitivebrasil.model.Orcamento;
import br.com.cognitivebrasil.util.Response;

@Service
@FeignClient(name = "ms-orcamento", url="localhost:8086", path = "/resource")
public interface OrcamentoFeignClient {

	@GetMapping(value = "budgets/despesa/{destino}")
	ResponseEntity<Response<Orcamento>> buscaOrcamentoPorDestino(@RequestHeader("Authorization") String bearerToken, @PathVariable("destino") String destino);
}
