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
	 * 1). �Ȳ���customer �����order  ����insert���
	 * 2). �Ȳ���order �����customer  ����insert��� ����update���
	 * ������һʱ, �����ȱ��� 1 ��һ��, �󱣴� n ��һ��, ��������������� UPDATE ���.
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
	 * Ĭ�������, ʹ���������ӵķ�ʽ����ȡ n ��һ�˵Ķ����������� 1 ��һ�˵Ķ���. 
	 * ��ʹ�� @ManyToOne �� fetch �������޸�Ĭ�ϵĹ������Եļ��ز���
	 * 1). FetchType.EAGER ��������
	 * 2). FetchType.LAZY ������
	 */
	@Test
	public void testFind() {
		Order order =entityManager.find(Order.class, 3);
		Customer customer=order.getCustomer();
		System.out.println(order.getOrderName());
		System.out.println(customer.getLastName());
	}
	
	/**
	 * ����ֱ��ɾ�� 1 ��һ��, ��Ϊ�����Լ��.
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
