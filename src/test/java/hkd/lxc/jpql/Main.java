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
	 * 默认情况下, 若只查询部分属性, 则将返回 Object[] 类型的结果. 或者 Object[] 类型的 List.
	 * 也可以在实体类中创建对应的构造器, 然后再 JPQL 语句中利用对应的构造器返回实体类的对象.
	 */
	@Test
	public void testPartlyProperties() {
		String jpql="SELECT new User(u.lastName) FROM User u where u.age>?";
		Query query=entityManager.createQuery(jpql).setParameter(1, 10);
		List<User> users=query.getResultList();
		System.out.println(users);
	}
	
	/**
	 * createNamedQuery 适用于在实体类前使用 @NamedQuery 标记的查询语句
	 */
	@Test
	public void testNamedQuery() {
		Query query=entityManager.createNamedQuery("namedQuery").setParameter("id", 10);
		User user=(User) query.getSingleResult();
		System.out.println(user);
	}
	
	/**
	 * createNativeQuery 适用于本地 SQL
	 */
	@Test
	public void testNativeQuery() {
		String sql="SELECT u.age FROM jpa_user u where u.id =?";
		Query query=entityManager.createNativeQuery(sql).setParameter(1, 10);
		Object result=query.getSingleResult();
		System.out.println(result);
	}
	
	/**
	 * 使用 hibernate 的查询缓存. 
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
	
	//查询 order 数量大于 2 的那些 Customer
	@Test
	public void testGroupBy(){
		String jpql = "SELECT o.customer FROM N21_BOTH_ORDER o "
				+ "GROUP BY o.customer "
				+ "HAVING count(o.id) >= 2";
		List<Customer> customers = entityManager.createQuery(jpql).getResultList();
			
		System.out.println(customers);
	}
	
	
	/**
	 * JPQL 的关联查询同 HQL 的关联查询. 
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
	 * 子查询
	 */
	@Test
	public void testSubQuery(){
		//查询所有 Customer 的 lastName 为 YY 的 Order
		String jpql = "SELECT o FROM N21_BOTH_ORDER o "
				+ "WHERE o.customer = (SELECT c FROM N21_BOTH_CUSTOMER c WHERE c.lastName = ?)";
		
		Query query = entityManager.createQuery(jpql).setParameter(1, "YY");
		List<Order> orders = query.getResultList();
		System.out.println(orders.size());
	}
	
	//使用 jpql 内建的函数
	@Test
	public void testJpqlFunction(){
		String jpql = "SELECT lower(c.email) FROM N21_BOTH_CUSTOMER c";
			
		List<String> emails = entityManager.createQuery(jpql).getResultList();
		System.out.println(emails);
	}
	
	/*
	 * 可以使用 JPQL 完成 UPDATE 和 DELETE 操作. 
	 */
	@Test
	public void testExecuteUpdate(){
		String jpql = "UPDATE N21_BOTH_CUSTOMER c SET c.lastName = ? WHERE c.id = ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 2);
			
		query.executeUpdate();
	}
	
}
