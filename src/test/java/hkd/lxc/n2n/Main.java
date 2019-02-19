package hkd.lxc.n2n;


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
	
	@Test
	public void testPersist() {
		Item i1 = new Item("i-1");
	
		Item i2 = new Item("i-2");
		
		Category c1 = new Category("C-1");
		
		Category c2 = new Category("C-2");
		
		//设置关联关系
		i1.getCategories().add(c1);
		i1.getCategories().add(c2);
		
		i2.getCategories().add(c1);
		i2.getCategories().add(c2);
		
		c1.getItems().add(i1);
		c1.getItems().add(i2);
		
		c2.getItems().add(i1);
		c2.getItems().add(i2);
		
		//执行保存
		entityManager.persist(i1);
		entityManager.persist(i2);
		entityManager.persist(c1);
		entityManager.persist(c2);
	}
	
	
   /**
	* 对于关联的集合对象, 默认使用懒加载的策略.
	* 使用维护关联关系的一方获取, 还是使用不维护关联关系的一方获取, SQL 语句相同. 
	*/
	@Test
	public void testFind(){
//		Item item = entityManager.find(Item.class, 1);
//		System.out.println(item.getItemName());
//			
//		System.out.println(item.getCategories().size());
			
		Category category = entityManager.find(Category.class, 1);
		System.out.println(category.getCategoryName());
		System.out.println(category.getItems().size());
		}
}
