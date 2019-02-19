package hkd.lxc.many2one_both;


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
	
	/**
	 * 若是双向 1-n 的关联关系, 执行保存时
	 * 若先保存  n 的一端, 再保存 1 的一端, 默认情况下, 会多出 n 条 UPDATE 语句.
	 * 若先保存 1 的一端, 则会多出 n 条 UPDATE 语句.(前提是在保存前对象互相关联)
	 * 在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系, 这样会有效的减少 SQL 语句. 
	 */
	@Test
	public void testPersist() {
		Customer customer=new Customer("cust_aa", "aa@aa.com", 20);
		Order order1=new Order("ord_aa");
		Order order2=new Order("ord_bb");
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        //customer.getOrders().add(order1);  //若注释掉此两项则不会有update语句
        //customer.getOrders().add(order2);  //
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
	}
}
