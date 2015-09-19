package com.fj25.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static  EntityManagerFactory factory = Persistence.createEntityManagerFactory("controleFinancas");

	public EntityManager getEm() {
		return factory.createEntityManager();
	}
}
