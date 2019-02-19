package hkd.lxc.base;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
 
@NamedQuery(name="namedQuery",query="SELECT u from User u where u.id=:id")
@Cacheable(true)  //二级缓存
@Table(name="JPA_USER")
@Entity
public class User {

	private Integer id;
	private String lastName;
	private String email;
	private int age;
	private Date createdTime;
	private Date birth;
	
	public User() {}

	
	
	
public User(String lastName) {
		super();
		this.lastName = lastName;
	}



public User(String lastName, String email, int age, Date createdTime,
			Date birth) {
	super();
	this.lastName = lastName;
	this.email = email;
	this.age = age;
	this.createdTime = createdTime;
	this.birth = birth;
	}


	@TableGenerator(name="ID_USER",
		table="jpa_id_generators",
		pkColumnName="PK_NAME",
		pkColumnValue="USER_ID",
		valueColumnName="PK_VALUE",
		allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="ID_USER")
//	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
	
	
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	

	//工具方法. 不需要映射为数据表的一列. 
	@Transient
	public String getInfo(){
		return "lastName: " + lastName + ", email: " + email;
	}

	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", age=" + age + ", createdTime=" + createdTime
				+ ", birth=" + birth + "]";
	}

}
