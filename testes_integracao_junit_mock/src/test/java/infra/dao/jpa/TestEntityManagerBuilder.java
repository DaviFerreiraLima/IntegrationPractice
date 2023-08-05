package infra.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEntityManagerBuilder {
	
	private static EntityManagerFactory emf;
	
	public static EntityManager getEntityManager() {
		if(emf == null)	emf = Persistence.createEntityManagerFactory("teste_integracao");
		return emf.createEntityManager();
	}
	
	public static void closeManager() {
		emf.close();
	}

}
