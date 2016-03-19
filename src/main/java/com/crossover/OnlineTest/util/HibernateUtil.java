package com.crossover.OnlineTest.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

public class HibernateUtil {
	private static final EntityManagerFactory emFactory;

	static {
		emFactory = Persistence.createEntityManagerFactory("com.crossover");
	}

	public static CriteriaBuilder getCriteriaBuilder() {
		CriteriaBuilder builder = emFactory.getCriteriaBuilder();
		return builder;
	}

	public static EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}
}
