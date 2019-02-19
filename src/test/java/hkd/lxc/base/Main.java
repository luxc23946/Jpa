package hkd.lxc.base;

import java.util.Date;

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
		//1. 创建 EntitymanagerFactory
		managerFactory=Persistence.createEntityManagerFactory("Jpa");
		//2. 创建 EntityManager. 类似于 Hibernate 的 SessionFactory
		entityManager=managerFactory.createEntityManager();
		//3. 开启事务
		transaction=entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destoty() {
		//5. 提交事务
		transaction.commit();
		//6. 关闭 EntityManager
		entityManager.close();
		//7. 关闭 EntityManagerFactory
		managerFactory.close();
	}
	
	/**
	 *  4. 进行持久化操作
	 * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态. 
	 * 和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常. 
	 */
	@Test
	public void testPersist(){
		User user=new User("Tom", "aa@aaa.com", 20, new Date(), new Date());
		entityManager.persist(user);
	}
	
	
	/**
	 * 类似于 hibernate 中 Session 的 get 方法. 
	 */
	@Test
	public void testFind() {
		User user=entityManager.find(User.class, 1);
		System.out.println(user);
	}
	
	
	/**
	 * 类似于 hibernate 中 Session 的 load 方法
	 * 懒加载
	 */
	@Test
	public void testGetReference() {
		User user=entityManager.getReference(User.class, 1);
		System.out.println(user.getClass());
		System.out.println(user);
		
	}
	
	
	
	/**
	 * 类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
	 * 但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
	 */
	@Test
	public void testRemove(){
		User user = entityManager.find(User.class, 1);
		entityManager.remove(user);
	}
	
	/**
	 * 1. 若传入的是一个临时对象  = persist
	 *   会创建一个新的对象, 把临时对象的属性复制到新的对象中, 然后对新的对象执行持久化操作. 所以
	 *   新的对象中有 id, 但以前的临时对象中没有 id. 
	 */
	@Test
	public void testMerge1(){
		User user=new User("Tom", "aa@aaa.com", 20, new Date(), new Date());
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	/**
	 * 2. 若传入的是一个游离对象, 即传入的对象有 OID.    
	 *   1). 若在 EntityManager 缓存中没有该对象
	 *   2). 若在数据库中也没有对应的记录
	 *   3). JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
	 *   4). 对新创建的对象执行 insert 操作. 
	 */
	@Test
	public void testMerge2(){
		User user=new User("Bob", "aa@aaa.com", 20, new Date(), new Date());
		user.setId(10);
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	/**
	 * 3. 若传入的是一个游离对象, 即传入的对象有 OID  
	 *   1). 若在 EntityManager 缓存中没有该对象
	 *   2). 若在数据库中有对应的记录
	 *   3). JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
	 *   4). 对查询到的对象执行 update 操作. 
	 */
	@Test
	public void testMerge3(){
		User user=new User("Bob", "aa@aaa.com", 20, new Date(), new Date());
		user.setId(1);
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	
	/**
	 * 4. 若传入的是一个游离对象, 即传入的对象有 OID.
	 *   1). 若在 EntityManager 缓存中有对应的对象
	 *   2). JPA 会把游离对象的属性复制到查询到EntityManager 缓存中的对象中.
	 *   3). EntityManager 缓存中的对象执行 UPDATE.
	 */
	@Test
	public void testMerge4(){
		User user=new User("Bob", "bb@bbb.com", 20, new Date(), new Date());
		user.setId(10);
		entityManager.find(User.class, 10);
		entityManager.merge(user);
	}
	
	/**
	 * 同 hibernate 中 Session 的 flush 方法. 
	 */
	@Test
	public void testFlush(){
		User user = entityManager.find(User.class, 1);
		System.out.println(user);
		user.setLastName("AA");
		entityManager.flush();
	}
	
	
	/**
	 * 同 hibernate 中 Session 的 refresh 方法. 
	 */
	@Test
	public void testRefresh(){
		User User=entityManager.find(User.class, 1);
		User=entityManager.find(User.class, 1);
		entityManager.refresh(User);
	}
	
}
