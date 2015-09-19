package com.fj25.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.fj25.modelo.Conta;

public class ContaDao {
	
	private EntityManager manager;

	public ContaDao(EntityManager manager) {
		this.manager = manager;
	}

	public void salva(Conta conta){
		this.manager.getTransaction().begin();
		this.manager.persist(conta);
		this.manager.getTransaction().commit();
		this.manager.close();;
	}
	
	public Conta busca(Long id){
		return this.manager.find(Conta.class, id);
	}
	
	public List<Conta> lista(){
		return manager.createQuery("from Conta").getResultList();
	}
	
}
