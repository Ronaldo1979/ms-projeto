package br.com.cognitivebrasil.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.cognitivebrasil.model.Orcamento;
import br.com.cognitivebrasil.model.Projeto;
import br.com.cognitivebrasil.model.Secretaria;
import br.com.cognitivebrasil.model.Usuario;
import br.com.cognitivebrasil.service.ProjetoService;
import br.com.cognitivebrasil.util.Response;
import br.com.cognitivebrasil.validate.ValidateProjeto;
import br.com.cognitivebrasil.validate.ValidateUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/resource")
@Api(value = "API Gestão Municipal de Projetos")
@CrossOrigin(origins = "*")
public class ProjetoAPI {

	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "project")
	@ApiOperation(value = "Cadastra Projeto",
			notes = "Cadastra um novo projeto")
	public ResponseEntity<Response<Projeto>> cadastraProjeto(@Validated @RequestBody String obj){
		
		System.out.println("***********************************");
		System.out.println(">>> Cadastrando Projeto...");
		System.out.println("***********************************");
		
		Projeto projeto = null;
		Response<Projeto> response = new Response<Projeto>();
		ValidateProjeto validate = new ValidateProjeto();
		List<String> listaErros = new ArrayList<String>();
		
		try {
			
			Gson gson = new Gson();
			JsonObject jsonObject = JsonParser.parseString(obj).getAsJsonObject();
			
			projeto = gson.fromJson(jsonObject.toString(), Projeto.class);
			
			if(projeto != null) {
				
				List<String> erros = validate.valida(projeto);
				
				if(erros == null || erros.isEmpty()) {
					
					Boolean projetoInvalido = false;
					
					//Busca secretaria
					Secretaria secretaria = projetoService.buscaSecretaria(projeto.getSecretariaId());
					
					//Verifica secretaria existente
					if(secretaria != null) {
						//Verifica de a secretaria encontra-se sob investigação
						if(secretaria.getSobInvestigacao() == true) {
							projetoInvalido = true;
							
							erros.add("A Secretaria ("+secretaria.getPasta().name()+") encontra-se sob investigação.");
							response.setErrors(erros);
							response.setData(projeto);
							
						}else {
						
							List<Projeto> listaProjetos = projetoService.listaProjeto();
							for(Projeto p : listaProjetos) {
								if(p.getTitulo().trim().contentEquals(projeto.getTitulo().trim()) && p.getPasta().name().trim().equalsIgnoreCase(projeto.getPasta().name().trim())) {
									projetoInvalido = true;
									
									erros.add("Já existe um projeto cadastrado com o titulo ("+projeto.getTitulo()+") para a pasta ("+projeto.getPasta()+")");
									response.setErrors(erros);
									response.setData(projeto);
								}
							}
							
							if(projetoInvalido == false) {
								
								//Verifica se existe orçamento para executar o projeto
								Orcamento orcamento = projetoService.buscaOrcamento(projeto.getPasta().name());
								
								Double valorDisponivel = 0.00;
								
								if(orcamento != null)
									valorDisponivel = (orcamento.getTotalOrcamento() - orcamento.getValorGasto());
								
								if(orcamento == null || valorDisponivel < projeto.getCustoProjeto()) {
									projetoInvalido = true;
									
									erros.add("Não existe orçamento disponível para execução do projeto ("+projeto.getTitulo()+")");
									response.setErrors(erros);
									response.setData(projeto);
								}
							}
							
						}
						
						if(projetoInvalido == false) {
							
							projeto = projetoService.cadastraProjeto(projeto);
							response.setData(projeto);
							
						}else {
							
							return ResponseEntity.badRequest().body(response);
						}
						
					}else {
						
						erros.add("Você não possui autorização para acessar a secretaría ou a mesma não existe!");
						response.setErrors(erros);
						response.setData(projeto);
						
						return ResponseEntity.badRequest().body(response);
					}
					
				}else {
					
					response.setErrors(erros);
					response.setData(projeto);
					
					return ResponseEntity.badRequest().body(response);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
			listaErros.add("Erro ao cadastrar projeto");
			response.setErrors(listaErros);
			
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "project")
	@ApiOperation(value = "Lista Projetos",
			notes = "Lista todos os projetos")
	public  List<Projeto> listaProjeto() {
		
		List<Projeto> projetos = null;
		
		try {
			projetos = projetoService.listaProjeto();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return projetos;
	}
	
	@GetMapping(value = "project/{projetoId}")
	@ApiOperation(value = "Busca Projeto por ID",
			notes = "Busca um projeto especifico de acordo com o ID")
	public ResponseEntity<Response<Projeto>> buscaProjeto(@PathVariable("projetoId") Long projetoId) {
		
		Response<Projeto> response = new Response<Projeto>();
		List<String> listaErros = new ArrayList<String>();
		Optional<Projeto> projeto = null;
		
		try {
		
			projeto = projetoService.buscaProjeto(projetoId);
			if(projeto != null && !projeto.isEmpty()) {
				response.setData(projeto.get());
				return ResponseEntity.ok(response);
			
			}else {
				listaErros.add("Projeto não enconatrado");
				response.setErrors(listaErros);
				return ResponseEntity.badRequest().body(response);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
			listaErros.add("Erro ao buscar projeto");
			response.setErrors(listaErros);
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PatchMapping(value = "project/{projetoId}/{valorProjeto}")
	@ApiOperation(value = "Atualiza Custo Projeto",
			notes = "Possibilita efetuar o ajuste do valor do projeto informando o valor atual do projeto")
	public ResponseEntity<Response<Projeto>> atualizaProjeto(@PathVariable("projetoId") Long projetoId, @PathVariable("valorProjeto") Double valorProjeto){
		
		System.out.println("****************************************");
		System.out.println(">>> Alterando projeto...");
		System.out.println("****************************************");
		
		Projeto projeto = null;
		Response<Projeto> response = new Response<Projeto>();
		List<String> listaErros = new ArrayList<String>();
		
		try {
			
			projeto = projetoService.buscaProjeto(projetoId).orElse(null);
			if(projeto != null) {
				
				projeto.setCustoProjeto(valorProjeto);
				projeto = projetoService.atualizaProjeto(projeto);
				
				response.setData(projeto);
			}else {
				
				listaErros.add("Projeto não encontrado!");
				response.setErrors(listaErros);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			
			listaErros.add("Erro ao atualizar projeto.");
			response.setErrors(listaErros);
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	//USER==================================================================//
	
		@PostMapping(value = "user")
		@ApiOperation(value = "Cadastra Usuário",
				notes = "Cadastra um novo usuário")
		public ResponseEntity<Response<Usuario>> cadastraUsuario(@Validated @RequestBody String obj){
			
			System.out.println("***********************************");
			System.out.println(">>> Cadastrando Usuario...");
			System.out.println("***********************************");
			
			Usuario usuario = null;
			Response<Usuario> response = new Response<Usuario>();
			ValidateUsuario validate = new ValidateUsuario();
			List<String> listaErros = new ArrayList<String>();
			
			try {
				
				Gson gson = new Gson();
				JsonObject jsonObject = JsonParser.parseString(obj).getAsJsonObject();
				
				usuario = gson.fromJson(jsonObject.toString(), Usuario.class);
				
				if(usuario != null) {
					
					List<String> erros = validate.valida(usuario);
					
					if(erros == null || erros.isEmpty()) {
						
						Optional<Usuario> userCadastrado = projetoService.buscaUsuario(usuario.getEmail());
						
						if(userCadastrado.isEmpty() || userCadastrado == null) {
							usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
							usuario = projetoService.cadastraUsuario(usuario);
							response.setData(usuario);
						}else {
							
							erros.add("Usuário " + usuario.getEmail() + " já cadastrado!");
							response.setErrors(erros);
							response.setData(usuario);
							return ResponseEntity.badRequest().body(response);
						}
						
					}else {
						
						response.setErrors(erros);
						response.setData(usuario);
						
						return ResponseEntity.badRequest().body(response);
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				
				listaErros.add("Erro ao cadastrar usuario");
				response.setErrors(listaErros);
				
				return ResponseEntity.badRequest().body(response);
			}
			
			return ResponseEntity.ok(response);
		}
		
		@GetMapping(value = "user")
		@ApiOperation(value = "Lista Usuário",
				notes = "Lista todos os usuários cadastrados")
		public  List<Usuario> listaUsuario() {
			
			List<Usuario> usuario = null;
			
			try {
				usuario = projetoService.listaUsuario();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return usuario;
		}
		
		@GetMapping(value = "user/{email}")
		@ApiOperation(value = "Busca Usuário por Email",
				notes = "Busca um usuário especifico com base no email informado")
		public ResponseEntity<Response<Usuario>> buscaUsuario(@PathVariable("email") String email) {
			
			Response<Usuario> response = new Response<Usuario>();
			List<String> listaErros = new ArrayList<String>();
			Optional<Usuario> usuario = null;
			
			try {
			
				usuario = projetoService.buscaUsuario(email);
				if(usuario != null && !usuario.isEmpty()) {
					response.setData(usuario.get());
					return ResponseEntity.ok(response);
				
				}else {
					listaErros.add("Usuario não encontrado");
					response.setErrors(listaErros);
					return ResponseEntity.badRequest().body(response);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				
				listaErros.add("Erro ao buscar secretaria");
				response.setErrors(listaErros);
				return ResponseEntity.badRequest().body(response);
			}
		}
		
		@PutMapping(value = "user")
		@ApiOperation(value = "Atualiza Usuário",
				notes = "Possibilita atualizar a senha do usuário")
		public ResponseEntity<Response<Usuario>> atualizaUsuario(@Validated @RequestBody String obj){
			
			System.out.println("***********************************");
			System.out.println(">>> Atualizando Usuario...");
			System.out.println("***********************************");
			
			Usuario usuario = null;
			Response<Usuario> response = new Response<Usuario>();
			ValidateUsuario validate = new ValidateUsuario();
			List<String> listaErros = new ArrayList<String>();
			
			try {
				
				Gson gson = new Gson();
				JsonObject jsonObject = JsonParser.parseString(obj).getAsJsonObject();
				
				usuario = gson.fromJson(jsonObject.toString(), Usuario.class);
				
				if(usuario != null) {
					
					List<String> erros = validate.valida(usuario);
					
					if(erros == null || erros.isEmpty()) {
						
						Optional<Usuario> userCadastrado = projetoService.buscaUsuario(usuario.getEmail());
						
						if( userCadastrado != null && !userCadastrado.isEmpty()) {
							if(usuario.getId() == null)
								usuario.setId(userCadastrado.get().getId());
							usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
							usuario = projetoService.atualizaUsuario(usuario);
							response.setData(usuario);
						}else {
							
							erros.add("Usuário " + usuario.getEmail() + " não existe!");
							response.setErrors(erros);
							response.setData(usuario);
							return ResponseEntity.badRequest().body(response);
						}
						
					}else {
						
						response.setErrors(erros);
						response.setData(usuario);
						
						return ResponseEntity.badRequest().body(response);
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				
				listaErros.add("Erro ao cadastrar usuario");
				response.setErrors(listaErros);
				
				return ResponseEntity.badRequest().body(response);
			}
			
			return ResponseEntity.ok(response);
		}
}
