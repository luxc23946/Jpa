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
	 * ���� 1-n������ϵ����ʱ,һ������update���	
	 * ��ʱ������ϵ�� 1 ��һ��ά�� ���һ���ڱ���ʱû�����,�������۱���˳�����,������update���
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
	 * Ĭ�϶Թ����Ķ��һ��ʹ�������ؼ�������
	 * ��ʹ�� @OneToMany �� fetch �������޸�Ĭ�ϵĹ������Եļ��ز���
	 */
	@Test
	public void testFind() {
		Customer customer=entityManager.find(Customer.class, 2);
		System.out.println(customer.getLastName());
		System.out.println(customer.getOrders().size());
	}
	
	/**
	 * Ĭ�������, ��ɾ�� 1 ��һ��, ����Ȱѹ����� n ��һ�˵�����ÿ�, Ȼ�����ɾ��.
	 * ����ͨ��  @OneToMany �� cascade �������޸�Ĭ�ϵ�ɾ������. 
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
