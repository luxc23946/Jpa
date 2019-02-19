package hkd.lxc.second_cache;



import hkd.lxc.base.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Main {

	private EntityManagerFactory managerFactory=null;
	private EntityManager entityManager=null;
	private EntityTransaction transaction=null;
	
	@Before
	public void init() {
		managerFactory=Persistence.createEntityManagerFactory("Jpa");
		entityManager=managerFactory.createEntityManager();
		transaction=entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destoty() {
		transaction.commit();
		entityManager.close();
		managerFactory.close();
	}
	
	@Test
	public void testSecondCache() {
		User user=entityManager.find(User.class, 10);
		transaction.commit();
		entityManager.close();
		entityManager=managerFactory.createEntityManager();
		transaction=entityManager.getTransaction();
		transaction.begin();
		
		user=entityManager.find(User.class, 10);
		System.out.println(user);
	}
	
}
