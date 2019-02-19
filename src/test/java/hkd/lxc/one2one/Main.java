package hkd.lxc.one2one;


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
	 *  ˫�� 1-1 �Ĺ�����ϵ, �����ȱ��治ά��������ϵ��һ��, ��û�������һ��, ���������� UPDATE ���.
	 */
	@Test
	public void testPersist() {
		Manager manager=new Manager("M_AA");
		Department department=new Department("D_AA");
		manager.setDept(department);
		department.setMgr(manager);
		entityManager.persist(manager);
		entityManager.persist(department);
		
	}
	
	/**
	 * 1.Ĭ�������, ����ȡά��������ϵ��һ��, ���ͨ���������ӻ�ȡ������Ķ���. 
	 * ������ͨ�� @OntToOne �� fetch �������޸ļ��ز���.
	 */
	@Test
	public void testFind(){
		Department dept = entityManager.find(Department.class, 1);
		System.out.println(dept.getDeptName());
		System.out.println(dept.getMgr().getClass().getName());
	}
	
	   /**
	    * Ĭ�������, ����ȡ��ά��������ϵ��һ��, ��Ҳ��ͨ���������ӻ�ȡ������Ķ���. 
	    * ����ͨ�� @OneToOne �� fetch �������޸ļ��ز���. ����Ȼ���ٷ��� SQL �������ʼ��������Ķ���
	    * ��˵���ڲ�ά��������ϵ��һ��, �������޸� fetch ����. 
	    */
		@Test
		public void testOneToOneFind2(){
			Manager mgr = entityManager.find(Manager.class, 1);
			System.out.println(mgr.getMgrName());
			
			System.out.println(mgr.getDept().getClass().getName());
		}
	
}
