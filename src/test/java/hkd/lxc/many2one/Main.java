package hkd.lxc.many2one;

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
	 * 1). 先插入customer 后插入order  三条insert语句
	 * 2). 先插入order 后插入customer  三条insert语句 两条update语句
	 * 保存多对一时, 建议先保存 1 的一端, 后保存 n 的一端, 这样不会多出额外的 UPDATE 语句.
	 */
	@Test
	public void testPersist() {
		Customer customer=new Customer("cust_aa", "aa@aa.com", 20);
		Order order1=new Order("ord_aa");
		Order order2=new Order("ord_bb");
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
	}
	
	
	/**
	 * 默认情况下, 使用左外连接的方式来获取 n 的一端的对象和其关联的 1 的一端的对象. 
	 * 可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	 * 1). FetchType.EAGER 左外连接
	 * 2). FetchType.LAZY 懒加载
	 */
	@Test
	public void testFind() {
		Order order =entityManager.find(Order.class, 3);
		Customer customer=order.getCustomer();
		System.out.println(order.getOrderName());
		System.out.println(customer.getLastName());
	}
	
	/**
	 * 不能直接删除 1 的一端, 因为有外键约束.
	 */
	public void testRemove() {
		Order order=entityManager.find(Order.class, 3);
		entityManager.remove(order);
		Customer customer=entityManager.find(Customer.class, 2);
		entityManager.remove(customer);
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testUpdate() {
		Order order=entityManager.find(Order.class, 3);
		order.getCustomer().setLastName("FFF");
	}
	
}
