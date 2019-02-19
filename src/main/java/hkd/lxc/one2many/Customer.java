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
	 * ӳ�䵥�� 1�Ե� ������ϵ
	 * ʹ�� @OneToMany ��ӳ��1�Զ�Ĺ�����ϵ
	 * ʹ�� @JoinColumn ��ӳ������е�����
	 * fetch:���ü������� �������� ���� ������
	 * cascade:�޸�Ĭ�ϵ�ɾ������
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
