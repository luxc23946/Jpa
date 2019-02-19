package hkd.lxc.one2many;


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
	 * 单项 1-n关联关系保存时,一定会多出update语句	
	 * 此时关联关系由 1 的一方维护 多的一方在保存时没有外键,所以无论保存顺序如何,都会有update语句
	 */
	@Test
	public void testPersist() {
		Customer customer=new Customer("cust_aa", "aa@aa.com", 20);
		Order order1=new Order("ord_aa");
		Order order2=new Order("ord_bb");
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}
	
	/**
	 * 默认对关联的多的一方使用懒加载检索策略
	 * 可使用 @OneToMany 的 fetch 属性来修改默认的关联属性的加载策略
	 */
	@Test
	public void testFind() {
		Customer customer=entityManager.find(Customer.class, 2);
		System.out.println(customer.getLastName());
		System.out.println(customer.getOrders().size());
	}
	
	/**
	 * 默认情况下, 若删除 1 的一端, 则会先把关联的 n 的一端的外键置空, 然后进行删除.
	 * 可以通过  @OneToMany 的 cascade 属性来修改默认的删除策略. 
	 */
	@Test
	public void testRemve() {
		Customer customer=entityManager.find(Customer.class, 3);
		entityManager.remove(customer);
	}
	
	@Test
	public void testUpdate() {
		Customer customer=entityManager.find(Customer.class, 4);
		customer.getOrders().iterator().next().setOrderName("XX");
	}
	
}
