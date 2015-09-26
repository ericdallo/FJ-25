package com.fj25.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.fj25.modelo.Movimentacao;

public class MovimentacaoDao {
	
	private EntityManager manager;

	public MovimentacaoDao(EntityManager entityManager) {
		this.manager= entityManager;
	}
	
	public void adiciona(Movimentacao movimentacao) {
		manager.getTransaction().begin();
		manager.persist(movimentacao);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public Movimentacao busca(Long id){
		return manager.find(Movimentacao.class, id);
	}
	
	public List<Movimentacao> lista(){
		return manager.createQuery("from Movimentacao").getResultList();
	}
	
	public void remove(Movimentacao movimentacao){
		manager.remove(movimentacao);
	}
	
}
