package br.com.cognitivebrasil.util;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import br.com.cognitivebrasil.enumerator.Pasta;
import br.com.cognitivebrasil.model.Projeto;

@Service
public class DadosTest {

	public void criaProjetoTest() {
		
		Projeto projeto = new Projeto();
		
		projeto.setCustoProjeto(0.00);
		projeto.setDescricaoProjeto("Contrução da Escola Municipal Jardim Glaucia");
		projeto.setPasta(Pasta.EDUCACAO);
		projeto.setTitulo("Projeto nova escola");
		projeto.setSecretariaId(2L);
		
		Gson gson = new Gson();
		
		System.out.println("PROJETO TESTE: " + gson.toJson(projeto));
	}
}
