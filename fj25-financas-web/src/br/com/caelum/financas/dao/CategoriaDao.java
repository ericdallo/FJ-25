package br.com.caelum.financas.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.modelo.Categoria;

public class CategoriaDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;

	public Categoria procura(Integer id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> lista(){
		return manager.createQuery("SELECT c from Categoria c").getResultList();
	}

}
