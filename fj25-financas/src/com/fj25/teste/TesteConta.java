package com.fj25.teste;

import javax.persistence.EntityManager;

import com.fj25.dao.ContaDao;
import com.fj25.modelo.Conta;
import com.fj25.util.JPAUtil;

public class TesteConta {
	
	private static ContaDao dao;
	
	public static void main(String[] args) {
		long inicio = System.currentTimeMillis();
		
		EntityManager manager = new JPAUtil().getEm();
		dao = new ContaDao(manager);
		
		//testeSalvar();
		testePesquisar();
		//testeRemove();
		
		long fim = System.currentTimeMillis();
		System.out.println("Executado em: " + (fim-inicio) + "ms");
	}

	private static void testeSalvar() {
		Conta conta = new Conta("NuBank", "0088", "Greg");
		dao.salva(conta);
	}

	private static void testePesquisar() {
		Conta encontrado = dao.busca(1L);
		System.out.println("Conta encontrada " + encontrado.getTitular());
	}
	
	private static void testeRemove() {
		Conta conta = dao.busca(1L);
		dao.remove(conta);
	}
}
