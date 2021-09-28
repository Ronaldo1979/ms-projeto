package br.com.cognitivebrasil.validate;

import java.util.ArrayList;
import java.util.List;
import br.com.cognitivebrasil.model.Usuario;

public class ValidateUsuario {
	
	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public List<String> valida(Usuario obj) {
		
		errors = new ArrayList<String>();
		
		try {
			
			if(obj == null) {
				errors.add("Os dados do usuaário não podem ser nulos.");
				return this.errors;
			}else {
				
				if(obj.getEmail() == null || obj.getEmail().isBlank())
					errors.add("Campo email inválido.");
				
				if(obj.getSenha() == null || obj.getSenha().isBlank())
					errors.add("Campo senha inválido.");
					
				if(obj.getPerfil() == null)
					errors.add("Campo perfil inválido.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.errors;
	}
}
