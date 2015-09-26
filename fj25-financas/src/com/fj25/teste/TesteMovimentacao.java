package com.fj25.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import com.fj25.dao.MovimentacaoDao;
import com.fj25.modelo.Conta;
import com.fj25.modelo.Movimentacao;
import com.fj25.modelo.TipoMovimentacao;
import com.fj25.util.JPAUtil;

public class TesteMovimentacao {
	
	private static EntityManager manager;
	
	public static void main(String[] args) {
		manager = new JPAUtil().getEm();
		//salvaMovimentacao();
		pesquisaMovimentacao();

		manager.close();
	}

	private static void salvaMovimentacao() {
		manager.getTransaction().begin();
		Conta conta = new Conta("NuBank", "666", "Greg");
		
		manager.persist(conta);
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setConta(conta);
		movimentacao.setDate(Calendar.getInstance());
		movimentacao.setDescricao("Pagamento do curso fj-25");
		movimentacao.setValor(new BigDecimal("49.99"));
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		manager.persist(movimentacao);
		manager.getTransaction().commit();
	}
	
	private static void pesquisaMovimentacao() {
		MovimentacaoDao dao = new MovimentacaoDao(manager);
		Movimentacao movimentacao = dao.busca(2L);
		System.err.println(movimentacao.getConta().getTitular());
	}
}
