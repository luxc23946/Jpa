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
	 *  双向 1-1 的关联关系, 建议先保存不维护关联关系的一方, 即没有外键的一方, 这样不会多出 UPDATE 语句.
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
	 * 1.默认情况下, 若获取维护关联关系的一方, 则会通过左外连接获取其关联的对象. 
	 * 但可以通过 @OntToOne 的 fetch 属性来修改加载策略.
	 */
	@Test
	public void testFind(){
		Department dept = entityManager.find(Department.class, 1);
		System.out.println(dept.getDeptName());
		System.out.println(dept.getMgr().getClass().getName());
	}
	
	   /**
	    * 默认情况下, 若获取不维护关联关系的一方, 则也会通过左外连接获取其关联的对象. 
	    * 可以通过 @OneToOne 的 fetch 属性来修改加载策略. 但依然会再发送 SQL 语句来初始化其关联的对象
	    * 这说明在不维护关联关系的一方, 不建议修改 fetch 属性. 
	    */
		@Test
		public void testOneToOneFind2(){
			Manager mgr = entityManager.find(Manager.class, 1);
			System.out.println(mgr.getMgrName());
			
			System.out.println(mgr.getDept().getClass().getName());
		}
	
}
