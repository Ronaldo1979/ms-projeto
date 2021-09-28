package br.com.cognitivebrasil.validate;

import java.util.ArrayList;
import java.util.List;
import br.com.cognitivebrasil.model.Projeto;

public class ValidateProjeto {

	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public List<String> valida(Projeto obj) {
		
		errors = new ArrayList<String>();
		
		try {
			
			if(obj == null) {
				errors.add("Os dados do projeto não podem ser nulos.");
				return this.errors;
			}else {
				if(obj.getCustoProjeto() == null)
					errors.add("Campo custo inválido.");
				
				if(obj.getDescricaoProjeto() == null || obj.getDescricaoProjeto().isBlank())
					errors.add("Campo descrição inválido.");
					
				if(obj.getPasta() == null)
					errors.add("Campo pasta inválido.");
				
				if(obj.getSecretariaId() == null)
					errors.add("Secretaría inválida.");
				
				if(obj.getTitulo() == null || obj.getTitulo().isBlank())
					errors.add("Campo titulo inválido.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.errors;
	}
}
