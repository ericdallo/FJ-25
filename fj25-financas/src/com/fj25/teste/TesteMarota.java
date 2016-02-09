package com.fj25.teste;

import java.util.List;

import javax.persistence.EntityManager;

import com.fj25.modelo.Conta;
import com.fj25.modelo.Movimentacao;
import com.fj25.util.JPAUtil;

public class TesteMarota {
	
	public static void main(String[] args) {
		EntityManager manager = new JPAUtil().getEm();
		
		Conta c1 = manager.find(Conta.class, 1L);
		
		List<Movimentacao> movimentacoes = c1.getMovimentacao();
		
		manager.close();
	}
}
