package br.com.cognitivebrasil.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;
import br.com.cognitivebrasil.MsGestaoMunicipalProjetoApplication;
import br.com.cognitivebrasil.enumerator.Origem;
import br.com.cognitivebrasil.enumerator.Pasta;
import br.com.cognitivebrasil.model.Orcamento;
import br.com.cognitivebrasil.model.Projeto;
import br.com.cognitivebrasil.model.Secretaria;
import br.com.cognitivebrasil.security.JwtAuthenticationEntryPoint;
import br.com.cognitivebrasil.security.JwtAuthenticationTokenFilter;
import br.com.cognitivebrasil.security.JwtTokenUtil;
import br.com.cognitivebrasil.security.WebSecurityConfig;
import br.com.cognitivebrasil.service.ProjetoService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjetoAPI.class)
@ContextConfiguration(classes={MsGestaoMunicipalProjetoApplication.class, 
							   WebSecurityConfig.class,
							   JwtAuthenticationEntryPoint.class,
							   WebSecurityConfig.class,
							   UserDetailsService.class,
							   JwtAuthenticationTokenFilter.class,
							   JwtTokenUtil.class})
class ProjetoAPITest {
	
	/*
	 Para realização dos testes a autenticação do Spring Security deverá ser ajustada para 
	 permitir acesso a todas as requisições, para isso deverá ser efetuado os seguintes ajustes antes de executar os testes
	 Na classe WebSecurityConfig substituir no metodo configure
	 .antMatchers("/resource/**").authenticated() por .antMatchers("/resource/**").permitAll()
	 */

	@Autowired
	private ProjetoAPI projetoAPI;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;
	
	@MockBean
	private ProjetoService projetoService;
	
	private List<Projeto> listaProjeto = null;
	private List<Secretaria> listaSecretaria = null;
	private List<Orcamento> listaOrcamento = null;
	private Projeto projeto = null;
	private Secretaria secretaria = null;
	private Orcamento orcamento = null;
	private MockMvc mockMvc = null;
	
	@BeforeEach
	@SuppressWarnings("deprecation")
	public void setup() {
		
		 mockMvc = MockMvcBuilders
		            .standaloneSetup(projetoAPI)
		            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
		            .build();

		    MockitoAnnotations.initMocks(this);
		    
		    this.carregaDadosTest();
	}

	@Test
	public void listaProjetosSucesso() {
		
		when(this.projetoService.listaProjeto())
			.thenReturn(listaProjeto);
		
		ResultActions response;
		try {
			response = mockMvc.perform(
			        get("/resource/project")
			                .contentType(MediaType.APPLICATION_JSON)
			               );
			
			response.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscaProjetoSucesso() {
		
		when(this.projetoService.buscaProjeto(1L))
			.thenReturn(Optional.of(listaProjeto.get(0)));
		
		ResultActions response;
		try {
			response = mockMvc.perform(
			        get("/resource/project/{projetoId}",1L)
			                .contentType(MediaType.APPLICATION_JSON)
			               );
			
			response.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscaProjetoError() {
		
		when(this.projetoService.buscaProjeto(2L))
			.thenReturn(Optional.of(listaProjeto.get(0)));
		
		ResultActions response;
		try {
			response = mockMvc.perform(
			        get("/resource/project/{projetoId}",1L)
			                .contentType(MediaType.APPLICATION_JSON)
			               );
			
			response.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cadastraProjetoSucesso() {
		
		Gson gson = new Gson();
		String projetoJson = gson.toJson(listaProjeto.get(1));
		
		when(this.projetoService.cadastraProjeto(listaProjeto.get(1)))
			.thenReturn(listaProjeto.get(1));
		
		when(this.projetoService.buscaSecretaria(listaProjeto.get(1).getSecretariaId()))
			.thenReturn(listaSecretaria.get(1));
		
		when(this.projetoService.buscaOrcamento(listaProjeto.get(1).getPasta().name()))
			.thenReturn(Optional.of(listaOrcamento.get(1)).get());
		
		
		ResultActions response;
		try {
			response = mockMvc.perform(
			        post("/resource/project")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(projetoJson)
			               );
			
			response.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cadastraProjetoErro() {
		
		Gson gson = new Gson();
		String projetoJson = gson.toJson(listaProjeto.get(1));
		
		when(this.projetoService.cadastraProjeto(listaProjeto.get(1)))
			.thenReturn(listaProjeto.get(1));
		
		when(this.projetoService.buscaSecretaria(listaProjeto.get(1).getSecretariaId()))
			.thenReturn(null);
		
		when(this.projetoService.buscaOrcamento(listaProjeto.get(1).getPasta().name()))
			.thenReturn(Optional.of(listaOrcamento.get(1)).get());
		
		
		ResultActions response;
		try {
			response = mockMvc.perform(
			        post("/resource/project")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(projetoJson)
			               );
			
			response.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void atualizaProjetoSucesso() {
		
		Gson gson = new Gson();
		String projetoJson = gson.toJson(listaProjeto.get(0));
		
		when(this.projetoService.atualizaProjeto(listaProjeto.get(0)))
			.thenReturn(listaProjeto.get(0));
		
		when(this.projetoService.buscaProjeto(listaProjeto.get(0).getId()))
			.thenReturn(Optional.of(listaProjeto.get(0)));
		
		ResultActions response;
		try {
			response = mockMvc.perform(
					patch("/resource/project/{projetoId}/{despesa}",listaProjeto.get(0).getId(),6500.00)
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(projetoJson)
			               );
			
			response.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para carregar os dados utilizados no mock
	 */
	private void carregaDadosTest() {
		
		//Criando lista de Secretarias
		listaSecretaria = new ArrayList<Secretaria>();
		
		secretaria = new Secretaria();
		secretaria.setId(1L);
		secretaria.setNotaPopulacao(8L);
		secretaria.setPasta(Pasta.SAUDE);
		secretaria.setResponsavel("Ana Quintella");
		secretaria.setSobInvestigacao(false);
		
		listaSecretaria.add(secretaria);
		
		secretaria.setId(2L);
		secretaria.setNotaPopulacao(9L);
		secretaria.setPasta(Pasta.SPORT);
		secretaria.setResponsavel("Aroldo");
		secretaria.setSobInvestigacao(false);
		
		listaSecretaria.add(secretaria);
		
		secretaria.setId(3L);
		secretaria.setNotaPopulacao(7L);
		secretaria.setPasta(Pasta.EDUCACAO);
		secretaria.setResponsavel("Suzy Carla");
		secretaria.setSobInvestigacao(false);
		
		listaSecretaria.add(secretaria);
		
		//Criando lista de projetos
		listaProjeto = new ArrayList<Projeto>();
		
		projeto = new Projeto();
		projeto.setCustoProjeto(22000.00);
		projeto.setDescricaoProjeto("Reestruturação do clube de regatas Vasco da Gama");
		projeto.setId(1L);
		projeto.setPasta(Pasta.SPORT);
		projeto.setSecretariaId(2L);
		projeto.setTitulo("A volta do Gigante da Colina");
		
		listaProjeto.add(projeto);
		
		projeto = new Projeto();
		projeto.setCustoProjeto(7000.00);
		projeto.setDescricaoProjeto("Campanha para angariar torcedores");
		projeto.setId(2L);
		projeto.setPasta(Pasta.SPORT);
		projeto.setSecretariaId(2L);
		projeto.setTitulo("Torcida Brasil");
		
		listaProjeto.add(projeto);
		
		projeto = new Projeto();
		projeto.setCustoProjeto(7000.00);
		projeto.setDescricaoProjeto("Atendimento domiciliar em comunidades carentes");
		projeto.setId(3L);
		projeto.setPasta(Pasta.SAUDE);
		projeto.setSecretariaId(3L);
		projeto.setTitulo("Saude em casa");
		
		listaProjeto.add(projeto);
		
		//Cria lista de orçamento
		listaOrcamento = new ArrayList<Orcamento>();
		
		orcamento = new Orcamento();
		orcamento.setDestino(Pasta.SAUDE);
		orcamento.setId(1L);
		orcamento.setOrigem(Origem.FEDERAL);
		orcamento.setTotalOrcamento(15000000.00);
		orcamento.setValorGasto(0.00);
		
		listaOrcamento.add(orcamento);
		
		orcamento = new Orcamento();
		orcamento.setDestino(Pasta.SPORT);
		orcamento.setId(2L);
		orcamento.setOrigem(Origem.FEDERAL);
		orcamento.setTotalOrcamento(10000000.00);
		orcamento.setValorGasto(0.00);
		
		listaOrcamento.add(orcamento);
		
		orcamento = new Orcamento();
		orcamento.setDestino(Pasta.EDUCACAO);
		orcamento.setId(3L);
		orcamento.setOrigem(Origem.FEDERAL);
		orcamento.setTotalOrcamento(8000000.00);
		orcamento.setValorGasto(0.00);
		
		listaOrcamento.add(orcamento);
	}


}
