package hkd.lxc.many2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
/**
 * @author luxc
 */
@Table(name="N21_CUSTOMER")
@Entity(name="N21_CUSTOMER")
public class Customer {

	private Integer id;
	private String lastName;
	private String email;
	private int age;
	
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
	

	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + "]";
	}

	

}
