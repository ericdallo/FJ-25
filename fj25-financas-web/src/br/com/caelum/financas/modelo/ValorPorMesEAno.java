package br.com.caelum.financas.modelo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;

public class ValorPorMesEAno {
	
	private int mes;
	private int ano;
	private BigDecimal valor;
	
	public ValorPorMesEAno(int mes, int ano, BigDecimal valor) {
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
	}
	
}
