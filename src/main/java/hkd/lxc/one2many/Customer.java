package hkd.lxc.one2many;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
/**
 * @author luxc
 */
@Table(name="12N_CUSTOMER")
@Entity(name="12N_CUSTOMER")
public class Customer {

	private Integer id;
	private String lastName;
	private String email;
	private int age;
	private Set<Order>orders=new HashSet<Order>();
	
	public Customer() {}

	public Customer( String lastName, String email, int age) {
		super();
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}


	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	
	@Column(name="LAST_NAME",length=50,unique=false,nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
	@Column(length=50,nullable=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	/**
	 * 映射单向 1对的 关联关系
	 * 使用 @OneToMany 来映射1对多的关联关系
	 * 使用 @JoinColumn 来映射外键列的名称
	 * fetch:配置检索策略 左外连接 还是 懒加载
	 * cascade:修改默认的删除策略
	 */
	@JoinColumn(name="CUSTOMER_ID")
	@OneToMany(fetch=FetchType.LAZY,cascade= {CascadeType.REMOVE})
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	

	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + "]";
	}

	

}
