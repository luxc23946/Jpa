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
	 * ����˫�� 1-n �Ĺ�����ϵ, ִ�б���ʱ
	 * ���ȱ���  n ��һ��, �ٱ��� 1 ��һ��, Ĭ�������, ���� n �� UPDATE ���.
	 * ���ȱ��� 1 ��һ��, ����� n �� UPDATE ���.(ǰ�����ڱ���ǰ���������)
	 * �ڽ���˫�� 1-n ������ϵʱ, ����ʹ�� n ��һ����ά��������ϵ, �� 1 ��һ����ά������ϵ, ��������Ч�ļ��� SQL ���. 
	 */
	@Test
	public void testPersist() {
		Customer customer=new Customer("cust_aa", "aa@aa.com", 20);
		Order order1=new Order("ord_aa");
		Order order2=new Order("ord_bb");
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        //customer.getOrders().add(order1);  //��ע�͵��������򲻻���update���
        //customer.getOrders().add(order2);  //
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
	}
}
