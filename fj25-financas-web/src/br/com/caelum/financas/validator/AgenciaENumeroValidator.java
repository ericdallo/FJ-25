package br.com.caelum.financas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.caelum.financas.modelo.Conta;

public class AgenciaENumeroValidator implements ConstraintValidator<AgenciaENumero, Conta>{

	@Override
	public void initialize(AgenciaENumero arg0) {
		
	}

	@Override
	public boolean isValid(Conta arg0, ConstraintValidatorContext arg1) {
		
		return false;
	}

}
