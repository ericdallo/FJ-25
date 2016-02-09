package br.com.caelum.financas.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ValorNegativoException extends RuntimeException {

	public ValorNegativoException(String msg) {
		super(msg);
	}

}
