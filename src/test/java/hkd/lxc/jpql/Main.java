package hkd.lxc.jpql;



import java.util.List;

import hkd.lxc.base.User;
import hkd.lxc.many2one_both.Customer;
import hkd.lxc.many2one_both.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.ejb.QueryHints;
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
	public void test() {
		String jpql="from User u where u.age>? ";
		Query query=entityManager.createQuery(jpql).setParameter(1, 10);
		List<User> users=query.getResultList();
		System.out.println(users);
		
	}
	
	/*
	 * Ĭ�������, ��ֻ��ѯ��������, �򽫷��� Object[] ���͵Ľ��. ���� Object[] ���͵� List.
	 * Ҳ������ʵ�����д�����Ӧ�Ĺ�����, Ȼ���� JPQL ��������ö�Ӧ�Ĺ���������ʵ����Ķ���.
	 */
	@Test
	public void testPartlyProperties() {
		String jpql="SELECT new User(u.lastName) FROM User u where u.age>?";
		Query query=entityManager.createQuery(jpql).setParameter(1, 10);
		List<User> users=query.getResultList();
		System.out.println(users);
	}
	
	/**
	 * createNamedQuery ��������ʵ����ǰʹ�� @NamedQuery ��ǵĲ�ѯ���
	 */
	@Test
	public void testNamedQuery() {
		Query query=entityManager.createNamedQuery("namedQuery").setParameter("id", 10);
		User user=(User) query.getSingleResult();
		System.out.println(user);
	}
	
	/**
	 * createNativeQuery �����ڱ��� SQL
	 */
	@Test
	public void testNativeQuery() {
		String sql="SELECT u.age FROM jpa_user u where u.id =?";
		Query query=entityManager.createNativeQuery(sql).setParameter(1, 10);
		Object result=query.getSingleResult();
		System.out.println(result);
	}
	
	/**
	 * ʹ�� hibernate �Ĳ�ѯ����. 
	 */
	@Test
	public void testQueryCache() {
		
		String jpql = "FROM User u WHERE u.age > ?";
		Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
		
		query.setParameter(1, 1);
		List<User> customers = query.getResultList();
		System.out.println(customers.size());
		
		query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
		
		query.setParameter(1, 1);
		customers = query.getResultList();
		System.out.println(customers.size());
		
	}
	
	@Test
	public void testOrderBy(){
		String jpql = "FROM User u WHERE u.age > ? ORDER BY u.age DESC";
		Query query = entityManager.createQuery(jpql).setParameter(1, 10);
		List<User> customers = query.getResultList();
		System.out.println(customers.size());
	}
	
	//��ѯ order �������� 2 ����Щ Customer
	@Test
	public void testGroupBy(){
		String jpql = "SELECT o.customer FROM N21_BOTH_ORDER o "
				+ "GROUP BY o.customer "
				+ "HAVING count(o.id) >= 2";
		List<Customer> customers = entityManager.createQuery(jpql).getResultList();
			
		System.out.println(customers);
	}
	
	
	/**
	 * JPQL �Ĺ�����ѯͬ HQL �Ĺ�����ѯ. 
	 */
	@Test
	public void testLeftOuterJoinFetch(){
		String jpql = "FROM N21_BOTH_CUSTOMER c LEFT OUTER JOIN FETCH c.orders WHERE c.id = ?";
		
		Customer customer = 
				(Customer) entityManager.createQuery(jpql).setParameter(1, 1).getSingleResult();
		System.out.println(customer.getLastName());
		System.out.println(customer.getOrders().size());
	}
	
	/**
	 * �Ӳ�ѯ
	 */
	@Test
	public void testSubQuery(){
		//��ѯ���� Customer �� lastName Ϊ YY �� Order
		String jpql = "SELECT o FROM N21_BOTH_ORDER o "
				+ "WHERE o.customer = (SELECT c FROM N21_BOTH_CUSTOMER c WHERE c.lastName = ?)";
		
		Query query = entityManager.createQuery(jpql).setParameter(1, "YY");
		List<Order> orders = query.getResultList();
		System.out.println(orders.size());
	}
	
	//ʹ�� jpql �ڽ��ĺ���
	@Test
	public void testJpqlFunction(){
		String jpql = "SELECT lower(c.email) FROM N21_BOTH_CUSTOMER c";
			
		List<String> emails = entityManager.createQuery(jpql).getResultList();
		System.out.println(emails);
	}
	
	/*
	 * ����ʹ�� JPQL ��� UPDATE �� DELETE ����. 
	 */
	@Test
	public void testExecuteUpdate(){
		String jpql = "UPDATE N21_BOTH_CUSTOMER c SET c.lastName = ? WHERE c.id = ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 2);
			
		query.executeUpdate();
	}
	
}
