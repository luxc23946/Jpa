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
		//1. ���� EntitymanagerFactory
		managerFactory=Persistence.createEntityManagerFactory("Jpa");
		//2. ���� EntityManager. ������ Hibernate �� SessionFactory
		entityManager=managerFactory.createEntityManager();
		//3. ��������
		transaction=entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destoty() {
		//5. �ύ����
		transaction.commit();
		//6. �ر� EntityManager
		entityManager.close();
		//7. �ر� EntityManagerFactory
		managerFactory.close();
	}
	
	/**
	 *  4. ���г־û�����
	 * ������ hibernate �� save ����. ʹ��������ʱ״̬��Ϊ�־û�״̬. 
	 * �� hibernate �� save �����Ĳ�֮ͬ��: �������� id, ����ִ�� insert ����, �����׳��쳣. 
	 */
	@Test
	public void testPersist(){
		User user=new User("Tom", "aa@aaa.com", 20, new Date(), new Date());
		entityManager.persist(user);
	}
	
	
	/**
	 * ������ hibernate �� Session �� get ����. 
	 */
	@Test
	public void testFind() {
		User user=entityManager.find(User.class, 1);
		System.out.println(user);
	}
	
	
	/**
	 * ������ hibernate �� Session �� load ����
	 * ������
	 */
	@Test
	public void testGetReference() {
		User user=entityManager.getReference(User.class, 1);
		System.out.println(user.getClass());
		System.out.println(user);
		
	}
	
	
	
	/**
	 * ������ hibernate �� Session �� delete ����. �Ѷ����Ӧ�ļ�¼�����ݿ����Ƴ�
	 * ��ע��: �÷���ֻ���Ƴ� �־û� ����. �� hibernate �� delete ����ʵ���ϻ������Ƴ� �������.
	 */
	@Test
	public void testRemove(){
		User user = entityManager.find(User.class, 1);
		entityManager.remove(user);
	}
	
	/**
	 * 1. ���������һ����ʱ����  = persist
	 *   �ᴴ��һ���µĶ���, ����ʱ��������Ը��Ƶ��µĶ�����, Ȼ����µĶ���ִ�г־û�����. ����
	 *   �µĶ������� id, ����ǰ����ʱ������û�� id. 
	 */
	@Test
	public void testMerge1(){
		User user=new User("Tom", "aa@aaa.com", 20, new Date(), new Date());
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	/**
	 * 2. ���������һ���������, ������Ķ����� OID.    
	 *   1). ���� EntityManager ������û�иö���
	 *   2). �������ݿ���Ҳû�ж�Ӧ�ļ�¼
	 *   3). JPA �ᴴ��һ���µĶ���, Ȼ��ѵ�ǰ�����������Ը��Ƶ��´����Ķ�����
	 *   4). ���´����Ķ���ִ�� insert ����. 
	 */
	@Test
	public void testMerge2(){
		User user=new User("Bob", "aa@aaa.com", 20, new Date(), new Date());
		user.setId(10);
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	/**
	 * 3. ���������һ���������, ������Ķ����� OID  
	 *   1). ���� EntityManager ������û�иö���
	 *   2). �������ݿ����ж�Ӧ�ļ�¼
	 *   3). JPA ���ѯ��Ӧ�ļ�¼, Ȼ�󷵻ظü�¼��һ���Ķ���, ��Ȼ���������������Ը��Ƶ���ѯ���Ķ�����.
	 *   4). �Բ�ѯ���Ķ���ִ�� update ����. 
	 */
	@Test
	public void testMerge3(){
		User user=new User("Bob", "aa@aaa.com", 20, new Date(), new Date());
		user.setId(1);
		user=entityManager.merge(user);
		System.out.println(user);
	}
	
	
	/**
	 * 4. ���������һ���������, ������Ķ����� OID.
	 *   1). ���� EntityManager �������ж�Ӧ�Ķ���
	 *   2). JPA ��������������Ը��Ƶ���ѯ��EntityManager �����еĶ�����.
	 *   3). EntityManager �����еĶ���ִ�� UPDATE.
	 */
	@Test
	public void testMerge4(){
		User user=new User("Bob", "bb@bbb.com", 20, new Date(), new Date());
		user.setId(10);
		entityManager.find(User.class, 10);
		entityManager.merge(user);
	}
	
	/**
	 * ͬ hibernate �� Session �� flush ����. 
	 */
	@Test
	public void testFlush(){
		User user = entityManager.find(User.class, 1);
		System.out.println(user);
		user.setLastName("AA");
		entityManager.flush();
	}
	
	
	/**
	 * ͬ hibernate �� Session �� refresh ����. 
	 */
	@Test
	public void testRefresh(){
		User User=entityManager.find(User.class, 1);
		User=entityManager.find(User.class, 1);
		entityManager.refresh(User);
	}
	
}
