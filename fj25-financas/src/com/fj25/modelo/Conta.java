package com.fj25.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity @Table(name="conta")
public class Conta {
	
	@Id @GeneratedValue
	private Long id;
	
	private String numero;
	private String agencia;
	private String titular;
	
	@Transient // Nao sera mapeado
	public static int contadorDeContas;
	
	@OneToMany(mappedBy="conta")
	private List<Movimentacao> movimentacoes = new ArrayList<>();
	
	public Conta(String numero, String agencia, String titular) {
		this.numero = numero;
		this.agencia = agencia;
		this.titular = titular;
	}
	
	@Deprecated //Hibernate eyes only
	protected Conta() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public List<Movimentacao> getMovimentacao() {
		return this.movimentacoes;
	}

}
